import tkinter as tk
from tkinter import messagebox, simpledialog, ttk
from tkcalendar import DateEntry,Calendar
from datetime import datetime, date
from sqlalchemy import inspect
from sqlalchemy.orm import joinedload
from sqlalchemy.exc import IntegrityError
from models import Base, User, Book, Author, Category, Borrowing, Rating, UserRole, BookCategory, Reader
from functions import show_table_data, add_user, edit_user, delete_user, add_book, refresh_user_list, add_author, add_borrowing, add_category, add_rating
import bcrypt
from db import session

# Mapping table models
table_models = {
    "users": User,
    "books": Book,
    "authors": Author,
    "borrowings": Borrowing,
    "ratings": Rating,
    "categories": Category,
    "book_categories": BookCategory,
    "readers": Reader
}

# Default admin creation
if not session.query(User).first():
    hashed = bcrypt.hashpw("admin".encode('utf-8'), bcrypt.gensalt())
    default_admin = User(username="admin", password_hash=hashed, role=UserRole.ADMIN)
    session.add(default_admin)
    session.commit()


# Login Function
def login(login_window, entry_login_username, entry_login_password): #????
    username = entry_login_username.get()
    password = entry_login_password.get()

    user = session.query(User).filter_by(username=username).first()
    if user and bcrypt.checkpw(password.encode('utf-8'), user.password_hash):
        login_window.destroy()
        open_main_app(user)
    else:
        messagebox.showerror("Login Error", "Invalid username or password.")       
              
def enter_table(table, root, current_user):
    root.destroy()

    table_window = tk.Tk()
    table_window.title(f"Table: {table.capitalize()}")
    table_window.geometry("1000x600")

    def go_back():
        table_window.destroy()
        open_main_app(current_user)

    frame_filter = tk.Frame(table_window)
    frame_filter.pack(side=tk.LEFT, fill=tk.Y, padx=10, pady=5)

    tk.Label(frame_filter, text="Filtr:").pack(anchor="w", padx=5)

    model = table_models.get(table)
    if not model:
        messagebox.showwarning("Error", "Model not found.")
        return
    
    model_columns = [column.name for column in model.__table__.columns]
    model_rels = [rel_name for rel_name in inspect(model).relationships.keys()]

    filter_var = tk.StringVar()
    filter_dropdown = ttk.Combobox(frame_filter, textvariable=filter_var, values=model_columns, state="readonly")
    filter_dropdown.current(0)
    filter_dropdown.pack(anchor="w", padx=5, pady=2)

    filter_entry = tk.Entry(frame_filter)
    filter_entry.pack(anchor="w", padx=5, pady=2)

    def apply_filter():
        selected_filter = filter_var.get()
        filter_value = filter_entry.get()
        query = session.query(model)
        if hasattr(model, selected_filter) and filter_value:
            query = query.filter(getattr(model, selected_filter).like(f"%{filter_value}%"))
        rows = query.all()
        selected_columns = [col for col, var in column_visibility.items() if var.get()]
        show_table_data(model, rows, listbox_data, session, selected_columns)

    tk.Button(frame_filter, text="Filtruj", command=apply_filter).pack(anchor="w", padx=5, pady=2)
    tk.Button(frame_filter, text="(Dodaj Dane)", command=lambda: open_insert_window(table)).pack(anchor="w", padx=5, pady=2)
    tk.Button(frame_filter, text="Edytuj Zaznaczone", command=lambda: open_edit_window(table)).pack(anchor="w", padx=5, pady=2)
    tk.Button(frame_filter, text="Usuń zaznaczone", command=lambda: delete_selected_record()).pack(anchor="w", padx=5, pady=2)

    frame_columns = tk.Frame(table_window)
    frame_columns.pack(side=tk.RIGHT, fill=tk.Y, padx=10, pady=5)

    tk.Label(frame_columns, text="Kolumny:").pack(anchor="w", padx=5)
    
    column_visibility = {col: tk.BooleanVar(value=True) for col in model_columns}
    rel_visibility = {col: tk.BooleanVar(value=True) for col in model_rels}
    
    def toggle_columns():
        selected_columns = [col for col, var in column_visibility.items() if var.get()]
        selected_rels = [rel for rel, var in rel_visibility.items() if var.get()]
        rows = session.query(model).all()
        show_table_data(model, rows, listbox_data, session, selected_columns, selected_rels)
    
    for col in model_columns:
        chk = ttk.Checkbutton(frame_columns, text=col, variable=column_visibility[col], command=toggle_columns)
        chk.pack(anchor="w", padx=5)
    for rel in model_rels:
        chk = ttk.Checkbutton(frame_columns, text=rel, variable=rel_visibility[rel], command=toggle_columns)
        chk.pack(anchor="w", padx=5)

    tk.Button(frame_filter, text="Wróć", command=go_back).pack(anchor="w", padx=5, pady=20)

    frame_data_display = tk.Frame(table_window)
    frame_data_display.pack(fill=tk.BOTH, expand=True, padx=10, pady=10)

    scrollbar_y = tk.Scrollbar(frame_data_display, orient=tk.VERTICAL)
    scrollbar_y.pack(side=tk.RIGHT, fill=tk.Y)

    scrollbar_x = tk.Scrollbar(frame_data_display, orient=tk.HORIZONTAL)
    scrollbar_x.pack(side=tk.BOTTOM, fill=tk.X)

    listbox_data = tk.Listbox(frame_data_display, yscrollcommand=scrollbar_y.set, xscrollcommand=scrollbar_x.set, width=100, height=30)
    listbox_data.pack(side=tk.LEFT, fill=tk.BOTH, expand=True)

    scrollbar_y.config(command=listbox_data.yview)
    scrollbar_x.config(command=listbox_data.xview)

    row_id_mapping = {}

    def show_table_data(model, rows, listbox, session, columns, rels):
        listbox.delete(0, tk.END)
        row_id_mapping.clear()
        for row in rows:
            row_data = [str(getattr(row, col)) for col in columns] + ["(" + ", ".join(category.name for category in session.query(model).options(joinedload(getattr(model, rel))).filter_by(id=getattr(row, 'id')).one_or_none().categories) + ")" for rel in rels]
            display_text = " | ".join(row_data)
            listbox.insert(tk.END, display_text)
            row_id_mapping[display_text] = row.id

    rows = session.query(model).all()
    show_table_data(model, rows, listbox_data, session, model_columns, model_rels)

    def delete_selected_record():
        if model == Borrowing and current_user.role != ADMIN:
            messagebox.showerror("Błąd","Cannot remove borrowings data")
            return
        selected_index = listbox_data.curselection()
        if not selected_index:
            messagebox.showwarning("Błąd", "Nie wybrano rekordu do usunięcia.")
            return
        selected_item = listbox_data.get(selected_index)
        record_id = row_id_mapping.get(selected_item)
        if not record_id:
            messagebox.showwarning("Błąd", "Nie znaleziono ID rekordu.")
            return
        try:
            record = session.get(model, int(record_id))
            session.delete(record)
            session.commit()
            messagebox.showinfo("Sukces", "Rekord został usunięty.")
            toggle_columns()
        except Exception as e:
            session.rollback()
            messagebox.showerror("Błąd", f"Nie udało się usunąć rekordu:\n{e}")

    def open_insert_window(table):
        insert_window = tk.Toplevel(table_window)
        insert_window.title(f"Dodaj dane do {table.capitalize()}")
        insert_window.geometry("400x500")

        model_columns = model.__initializable__

        frame_input = tk.Frame(insert_window)
        frame_input.pack(fill=tk.BOTH, expand=True, padx=10, pady=10)

        entry_vars = {}
        for col in model_columns:
            tk.Label(frame_input, text=col.capitalize()).pack(anchor="w", padx=5)
            var = tk.StringVar()
            entry_vars[col] = var
            tk.Entry(frame_input, textvariable=var).pack(anchor="w", padx=5, pady=2)


        def add_data():
            data_to_add = {col: var.get() for col, var in entry_vars.items() if var.get()}
            try:
                new_record = model()
                for col, var in data_to_add.items():
                    if var and var != 'None':
                        try:
                            setattr(new_record, col, datetime.strptime(var, '%Y-%m-%d').date())
                        except:
                            setattr(new_record, col, var)
                session.add(new_record)
                session.commit()
                messagebox.showinfo("Sukces", "Dane zostały dodane.")
                insert_window.destroy()
                toggle_columns()
            except Exception as e:
                session.rollback()
                messagebox.showerror("Błąd", f"Niepoprawne dane:\n{e}")

        tk.Button(frame_input, text="Dodaj", command=add_data).pack(anchor="w", padx=5, pady=10)
        tk.Button(frame_input, text="Zamknij", command=insert_window.destroy).pack(anchor="w", padx=5, pady=10)

        insert_window.mainloop()

        
    def open_edit_window(table):
        selected_index = listbox_data.curselection()
        if not selected_index:
            messagebox.showwarning("Błąd", "Nie wybrano rekordu do edycji.")
            return
        selected_item = listbox_data.get(selected_index)
        record_id = row_id_mapping.get(selected_item)
        if not record_id:
            messagebox.showwarning("Błąd", "Nie znaleziono ID rekordu.")
            return

        record = session.get(model, int(record_id))
        
        edit_window = tk.Toplevel(table_window)
        edit_window.title(f"Edytuj dane z {table.capitalize()}")
        edit_window.geometry("400x500")
        
        model_columns = model.__editable__

        frame_input = tk.Frame(edit_window)
        frame_input.pack(fill=tk.BOTH, expand=True, padx=10, pady=10)

        entry_vars = {}
        for col in model_columns:
            tk.Label(frame_input, text=col.capitalize()).pack(anchor="w", padx=5)
            var = tk.StringVar()
            var.set(getattr(record, col))
            entry_vars[col] = var
            tk.Entry(frame_input, textvariable=var).pack(anchor="w", padx=5, pady=2)

        def edit_data():
            try:
                for col, var in entry_vars.items():
                    if var.get() and var.get() != 'None':
                        try:
                            setattr(record, col, datetime.strptime(var.get(), '%Y-%m-%d').date())
                        except:
                            setattr(record, col, var.get())
                
                session.commit()
                messagebox.showinfo("Sukces", "Dane zostały zedytowane.")
                edit_window.destroy()
                toggle_columns()
            except Exception as e:
                session.rollback()
                messagebox.showerror("Błąd", f"Niepoprawne dane:\n{e}")

        tk.Button(frame_input, text="Potwierdz", command=edit_data).pack(anchor="w", padx=5, pady=10)
        tk.Button(frame_input, text="Zamknij", command=edit_window.destroy).pack(anchor="w", padx=5, pady=10)

        edit_window.mainloop()


# Open main application
def open_main_app(current_user):
    root = tk.Tk()
    root.title("Zarządzanie Biblioteką")
    
    def is_admin():
        return current_user.role == UserRole.ADMIN

    # User management section (Admin only)
    if is_admin():
        global entry_username, entry_password, listbox_users
        frame_user = tk.Frame(root)
        frame_user.pack(padx=20, pady=10)

        tk.Label(frame_user, text="Username:").grid(row=0, column=0)
        entry_username = tk.Entry(frame_user)
        entry_username.grid(row=0, column=1)

        tk.Label(frame_user, text="Password:").grid(row=1, column=0)
        entry_password = tk.Entry(frame_user, show="*")
        entry_password.grid(row=1, column=1)

        listbox_users = tk.Listbox(frame_user)
        listbox_users.grid(row=3, columnspan=2)
        refresh_user_list(listbox_users, session)
        
        tk.Button(frame_user, text="Add User", command=lambda: add_user(entry_username, entry_password, listbox_users, session)).grid(row=2, columnspan=2)
        
        button_edit_user = tk.Button(frame_user, text="Edytuj użytkownika", command=lambda: edit_user(listbox_users, session))
        button_edit_user.grid(row=4, columnspan=2)

        button_delete_user = tk.Button(frame_user, text="Usuń użytkownika", command=lambda: delete_user(current_user, listbox_users, session))
        button_delete_user.grid(row=5, columnspan=2)

    # Strona główna - wybór tabeli
    frame_main = tk.Frame(root)
    frame_main.pack(padx=20, pady=10)

    label_main = tk.Label(frame_main, text="Wybierz tabelę do przeglądania:")
    label_main.pack()

    button_books = tk.Button(frame_main, text="Książki", command=lambda: enter_table("books", root, current_user))
    button_books.pack()

    button_authors = tk.Button(frame_main, text="Autorzy", command=lambda: enter_table("authors", root, current_user))
    button_authors.pack()

    button_borrowings = tk.Button(frame_main, text="Wypożyczenia", command=lambda: enter_table("borrowings", root, current_user))
    button_borrowings.pack()

    button_ratings = tk.Button(frame_main, text="Oceny", command=lambda: enter_table("ratings", root, current_user))
    button_ratings.pack()

    button_categories = tk.Button(frame_main, text="Kategorie", command=lambda: enter_table("categories", root, current_user))
    button_categories.pack()

    button_bookcategories = tk.Button(frame_main, text="Kategorie Ksiazek", command=lambda: enter_table("book_categories", root, current_user))
    button_bookcategories.pack()

    button_readers = tk.Button(frame_main, text="Czytelnicy", command=lambda: enter_table("readers", root, current_user))
    button_readers.pack()

    root.mainloop()
