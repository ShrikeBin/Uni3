#include <simulation.hpp>

#include <vector>
#include <random>
#include <set>
#include <algorithm>

#include <iostream>

int get_index_ballanced(std::uniform_int_distribution<int>& dist,
                        std::mt19937& rng,
                        const std::vector<int>& bins,
                        int d)
{
    std::set<int> unique_index;
    while(unique_index.size() < d)
    {
        unique_index.insert(dist(rng));
    }

    auto min_index = std::min_element(unique_index.begin(), unique_index.end(), 
    [&bins](auto a, auto b)
    { 
        return bins.at(a) < bins.at(b); 
    });

    return *min_index;
}

single_simulation_results single_simulate_ballanced(int n, int d)
{
    single_simulation_results results{ 0 };

    std::random_device rd;
    std::mt19937 rng(rd());
    std::uniform_int_distribution<int> bin(0, n - 1);

    std::vector<int> bins(n, 0);
    int m = 0;

    {
        int index;

        for(int i = 0; i < n; ++i)
        {
            index = get_index_ballanced(bin, rng, bins, d);
            ++m;
            ++bins.at(index);
        }

        results.L = *std::max_element(bins.begin(), bins.end());
    }

    return results;
}

full_simulation_results full_simulate_ballanced(int tries, int d)
{
    full_simulation_results results;

    for(int n = 1000; n <= 100000; n += 1000)
    {
        for(int k = 1; k <= tries; ++k)
        {
            const single_simulation_results sim = single_simulate_ballanced(n, d);

            results.add(n, sim);
        }
        std::cout << n << std::endl;
    }
    results.calculate_avg(tries);

    return results;
}

void full_simulation_results::add(int n, const single_simulation_results& result)
{
    L.x.push_back(n);
    L.y.push_back(result.L);
}

void full_simulation_results::calculate_avg(int tries)
{
    for(int i = 0; i < L.x.size(); i += tries)
    {
        int sum = std::accumulate(L.y.begin() + i, L.y.begin() + i + tries, 0);

        avg_L.x.push_back(L.x.at(i));
        avg_L.y.push_back(sum / (double)tries);
    }
}
