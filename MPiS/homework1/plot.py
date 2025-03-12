import numpy as np
import matplotlib.pyplot as plt
import math
import time

# Mersenne Twister
rng = np.random.default_rng()  

def f(x):
    return np.sin(x)  

def g(x):
    return 4*x*((1-x)**3)

def h(x):
    return x ** (1/3)

def generate_point(left, right, top, rng):
    point = [] 
    point.append(rng.uniform(left, right))
    point.append(rng.uniform(0, top)) 
    return point

def monte_carlo(func, n, left, right, top, rng):
    inside = 0
    for i in range(0, n):
        point = generate_point(left, right, top, rng)
        if (func(point[0]) > point[1]):
            inside += 1
    return (inside/n)*(right-left)*(top)  # % of points * the full field

def run_f(n, repetitions):
    return [monte_carlo(f, n, 0, math.pi, 1, rng) for _ in range(repetitions)]

def run_g(n, repetitions):
    return [monte_carlo(g, n, 0, 1, 0.43, rng) for _ in range(repetitions)]

def run_h(n, repetitions):
    return [monte_carlo(h, n, 0, 8, 2, rng) for _ in range(repetitions)]

# True values of the integrals
true_value_f = 2
true_value_g = 0.2
true_value_h = 12

n_values = range(50, 5001, 50)

# Prepare plots
plt.figure(figsize=(15, 15))

start_time = time.time()

# Plot for f(x) - 5 runs
plt.subplot(3, 2, 1)
f_results_5 = [run_f(n, 5) for n in n_values]
for n, results in zip(n_values, f_results_5):
    plt.scatter([n]*len(results), results, color='cyan', alpha=0.6, s=10, label='Individual Result' if n == 50 else "")  # Scatter for 5 runs
f_avg_5 = [np.mean(res) for res in f_results_5]
plt.scatter(n_values, f_avg_5, label='Average (5 runs)', color='midnightblue', linewidth=2)
plt.axhline(y=true_value_f, color='lime', linestyle='-', label='True Value', linewidth=2)
plt.title("Monte Carlo Simulation of f(x) = sin(x) (5 runs)")
plt.xlabel("n")
plt.ylabel("Monte Carlo Result")
plt.legend()
plt.grid()

# Plot for f(x) - 50 runs
plt.subplot(3, 2, 2)
f_results_50 = [run_f(n, 50) for n in n_values]
for n, results in zip(n_values, f_results_50):
    plt.scatter([n]*len(results), results, color='cyan', alpha=0.6, s=10, label='Individual Result' if n == 50 else "")  # Scatter for 50 runs
f_avg_50 = [np.mean(res) for res in f_results_50]
plt.scatter(n_values, f_avg_50, label='Average (50 runs)', color='midnightblue', linewidth=2)
plt.axhline(y=true_value_f, color='lime', linestyle='-', label='True Value', linewidth=2)
plt.title("Monte Carlo Simulation of f(x) = sin(x) (50 runs)")
plt.xlabel("n")
plt.ylabel("Monte Carlo Result")
plt.legend()
plt.grid()

# Plot for g(x) - 5 runs
plt.subplot(3, 2, 3)
g_results_5 = [run_g(n, 5) for n in n_values]
for n, results in zip(n_values, g_results_5):
    plt.scatter([n]*len(results), results, color='cyan', alpha=0.6, s=10, label='Individual Result' if n == 50 else "")  # Scatter for 5 runs
g_avg_5 = [np.mean(res) for res in g_results_5]
plt.scatter(n_values, g_avg_5, label='Average (5 runs)', color='midnightblue', linewidth=2)
plt.axhline(y=true_value_g, color='lime', linestyle='-', label='True Value', linewidth=2)
plt.title("Monte Carlo Simulation of g(x) = 4x(1-x)^3 (5 runs)")
plt.xlabel("n")
plt.ylabel("Monte Carlo Result")
plt.legend()
plt.grid()

# Plot for g(x) - 50 runs
plt.subplot(3, 2, 4)
g_results_50 = [run_g(n, 50) for n in n_values]
for n, results in zip(n_values, g_results_50):
    plt.scatter([n]*len(results), results, color='cyan', alpha=0.6, s=10, label='Individual Result' if n == 50 else "")  # Scatter for 50 runs
g_avg_50 = [np.mean(res) for res in g_results_50]
plt.scatter(n_values, g_avg_50, label='Average (50 runs)', color='midnightblue', linewidth=2)
plt.axhline(y=true_value_g, color='lime', linestyle='-', label='True Value', linewidth=2)
plt.title("Monte Carlo Simulation of g(x) = 4x(1-x)^3 (50 runs)")
plt.xlabel("n")
plt.ylabel("Monte Carlo Result")
plt.legend()
plt.grid()

# Plot for h(x) - 5 runs
plt.subplot(3, 2, 5)
h_results_5 = [run_h(n, 5) for n in n_values]
for n, results in zip(n_values, h_results_5):
    plt.scatter([n]*len(results), results, color='cyan', alpha=0.6, s=10, label='Individual Result' if n == 50 else "")  # Scatter for 5 runs
h_avg_5 = [np.mean(res) for res in h_results_5]
plt.scatter(n_values, h_avg_5, label='Average (5 runs)', color='midnightblue', linewidth=2)
plt.axhline(y=true_value_h, color='lime', linestyle='-', label='True Value', linewidth=2)
plt.title("Monte Carlo Simulation of h(x) = x^(1/3) (5 runs)")
plt.xlabel("n")
plt.ylabel("Monte Carlo Result")
plt.legend()
plt.grid()

# Plot for h(x) - 50 runs
plt.subplot(3, 2, 6)
h_results_50 = [run_h(n, 50) for n in n_values]
for n, results in zip(n_values, h_results_50):
    plt.scatter([n]*len(results), results, color='cyan', alpha=0.6, s=10, label='Individual Result' if n == 50 else "")  # Scatter for 50 runs
h_avg_50 = [np.mean(res) for res in h_results_50]
plt.scatter(n_values, h_avg_50, label='Average (50 runs)', color='midnightblue', linewidth=2)
plt.axhline(y=true_value_h, color='lime', linestyle='-', label='True Value', linewidth=2)
plt.title("Monte Carlo Simulation of h(x) = x^(1/3) (50 runs)")
plt.xlabel("n")
plt.ylabel("Monte Carlo Result")
plt.legend()
plt.grid()

# Adjust layout and save the plot
plt.tight_layout()
plt.savefig('monte_carlo_simulations.png', format='png', dpi=300)

end_time = time.time()
print(f"Execution Time: {end_time - start_time:.2f} seconds")
