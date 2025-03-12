package eu.jpereira.trainings.designpatterns.structural.facade.model;

import eu.jpereira.trainings.designpatterns.structural.facade.BookstoreFacade;
import eu.jpereira.trainings.designpatterns.structural.facade.service.*;

public class DefaultBookstoreFacade implements BookstoreFacade {

    private CustomerDBService customerService;
    private BookDBService bookService;
    private OrderingService orderingService;
    private WharehouseService warehouseService;
    private CustomerNotificationService notificationService;

    public void setCustomerService(CustomerDBService customerService) {
        this.customerService = customerService;
    }

    public void setBookService(BookDBService bookService) {
        this.bookService = bookService;
    }

    public void setOrderingService(OrderingService orderingService) {
        this.orderingService = orderingService;
    }

    public void setWarehouseService(WharehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    public void setNotificationService(CustomerNotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Override
    public void placeOrder(String customerId, String isbn) {
        // Find customer and book
        Customer customer = customerService.findCustomerById(customerId);
        Book book = bookService.findBookByISBN(isbn);

        // Create order
        Order order = orderingService.createOrder(customer, book);

        // Dispatch order
        DispatchReceipt receipt = warehouseService.dispatch(order);

        // Notify customer
        notificationService.notifyClient(receipt);
    }
}
