#include <simulation.hpp>

#include <vector>
#include <random>
#include <set>
#include <algorithm>

#include <iostream>


void single_simulate_sort(simulation_array& array)
{
    array.results = {0, 0};

    for(int j = 1; j < array.size(); ++j)
    {
        int key = array.at(j);

        int i = j - 1;
        while(i >= 0 && array.sim_is_bigger(i, key))
        {
            array.sim_insert(i + 1, array.at(i));
            --i;
        }
        array.sim_insert(i + 1, key);
    }
}

full_simulation_results full_simulate_sort(int tries)
{
    full_simulation_results results;

    std::random_device rd;
    std::mt19937 rng(rd());

    for(int n = 100; n <= 10000; n += 100)
    {
        simulation_array array(n);
        for(int i = 1; i <= n; ++i)
        {
            array.at(i - 1) = i;
        }

        for(int k = 1; k <= tries; ++k)
        {
            std::shuffle(array.begin(), array.end(), rng);

            single_simulate_sort(array);

            results.add(n, array.results);
        }
        std::cout << n << std::endl;
    }
    results.calculate_avg(tries);

    return results;
}

void full_simulation_results::add(int n, const single_simulation_results& result)
{
    cmp.x.push_back(n);
    s.x.push_back(n);

    cmp.y.push_back(result.cmp);
    s.y.push_back(result.s);
}

void full_simulation_results::calculate_avg(int tries)
{
    for(int i = 0; i < cmp.x.size(); i += tries)
    {
        int sum = std::accumulate(cmp.y.begin() + i, cmp.y.begin() + i + tries, 0);

        avg_cmp.x.push_back(cmp.x.at(i));
        avg_cmp.y.push_back(sum / (double)tries);


        sum = std::accumulate(s.y.begin() + i, s.y.begin() + i + tries, 0);

        avg_s.x.push_back(s.x.at(i));
        avg_s.y.push_back(sum / (double)tries);
    }
}
