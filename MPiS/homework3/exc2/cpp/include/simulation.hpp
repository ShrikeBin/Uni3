#ifndef SIMULATION_HPP
#define SIMULATION_HPP

#include <axis.hpp>

struct single_simulation_results
{
    int cmp;
    int s;
};

struct full_simulation_results
{
    axis<int> cmp;
    axis<double> avg_cmp;

    axis<int> s;
    axis<double> avg_s;

    void add(int n, const single_simulation_results& result);
    
    void calculate_avg(int tries);
};

class simulation_array : public std::vector<int>
{
public:
    using std::vector<int>::vector; //inherit constructors

    single_simulation_results results;

    bool sim_is_bigger(int index, int value)
    {
        ++results.cmp;
        return this->at(index) > value;
    }

    void sim_insert(int index, int value)
    {
        ++results.s;
        this->at(index) = value;
    }
};

void single_simulate_sort(simulation_array& array);

full_simulation_results full_simulate_sort(int tries);

#endif