#ifndef SIMULATION_HPP
#define SIMULATION_HPP

#include <axis.hpp>

struct single_simulation_results
{
    int L;  // maximum load
};

struct full_simulation_results
{
    axis<int> L;
    axis<double> avg_L;

    void add(int n, const single_simulation_results& result);
    
    void calculate_avg(int tries);
};

single_simulation_results single_simulate_ballanced(int n, int d);

full_simulation_results full_simulate_ballanced(int tries, int d);

#endif