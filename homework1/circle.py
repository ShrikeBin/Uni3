import numpy as np
import matplotlib.pyplot as plt
import time
import math

# Mersenne Twister
rng = np.random.default_rng()  

def f(x):
    return np.sin(x)  

def generate_point(rng):
    point = [] 
    point.append(rng.uniform(0, 1))
    point.append(rng.uniform(0, 1)) 
    return point

def monte_carlo_pi(n, rng):
    inside = 0
    for i in range(0, n):
        point = generate_point(rng)
        if (((point[0]**2)+(point[1]**2))<= 1):
            inside += 1
    return (inside/n)*4

def run(n, repetitions):
    return [monte_carlo_pi(n,rng) for _ in range(repetitions)]

true_value = math.pi

n_values = range(50, 5001, 50)
start_time = time.time()

# Prepare plots
plt.figure(figsize=(15, 15))

f_results_100 = [run(n, 100) for n in n_values]
for n, results in zip(n_values, f_results_100):
    plt.scatter([n]*len(results), results, color='cyan', alpha=0.5, s=10, label='Individual Result' if n == 50 else "")
f_avg_100 = [np.mean(res) for res in f_results_100]
plt.scatter(n_values, f_avg_100, label='Average (100 runs)', color='midnightblue', linewidth=2)
plt.axhline(y=true_value, color='lime', linestyle='-', label='True Value', linewidth=2)
plt.title("Monte Carlo Simulation of pi (100 runs)")
plt.xlabel("n")
plt.ylabel("Monte Carlo Result")
plt.legend()
plt.tight_layout()
plt.savefig('pi_approx.png', format='png', dpi=600)

end_time = time.time()
print(f"Execution Time: {end_time - start_time:.2f} seconds")