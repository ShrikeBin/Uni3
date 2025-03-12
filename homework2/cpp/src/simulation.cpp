#include <simulation.hpp>

#include <vector>
#include <random>

single_simulation_results single_simulate(int n)
{
    single_simulation_results results{ 0, 0, 0, 0 };

    std::random_device rd;
    std::mt19937 rng(rd());
    std::uniform_int_distribution<int> bin(0, n - 1);

    std::vector<int> bins(n, 0);

    int tracker = 0;

    {
        int have_one = 0;
        int have_two = 0;

        int index;

        for(int i = 0; i < n; i++)
        {
            index = bin(rng);
            tracker++;

            if(bins.at(index) == 0)
            {
                have_one++;
            }
            else if(bins.at(index) == 1)
            {
                if(results.B == 0) // first collision
                {
                    results.B = tracker;
                }
                
                have_two++;
            }

            bins.at(index)++;
        }

        results.U = (n - have_one); // empty bins after n balls

        if(results.B == 0) // sure collision (unlikely)
        {
            results.B = n + 1;
        }
        
        while(have_one < n)
        {
            index = bin(rng);
            tracker++;

            if(bins.at(index) == 0)
            {
                have_one++;
            }
            else if(bins.at(index) == 1)
            {
                have_two++;
            }

            bins.at(index)++;
        }

        results.C = tracker; // all bins have one ball

        while(have_two < n)
        {
            index = bin(rng);
            tracker++;

            if(bins.at(index) == 1)
            {
                ++have_two;
            }

            ++bins.at(index);
        }
        results.D = tracker; // all bins have two balls
    }

    results.D_C = results.D - results.C;

    return results;
}

full_simulation_results full_simulate(int tries)
{
    full_simulation_results results;

    for(int n = 1000; n <= 100000; n += 1000)
    {
        for(int k = 1; k <= tries; ++k)
        {
            single_simulation_results sim = single_simulate(n);

            results.add(n, sim);
        }
    }
    results.calculate_avg(tries);

    return results;
}


//struct functions

void full_simulation_results::add(int n, const single_simulation_results& result)
{
    B.x.push_back(n);
    U.x.push_back(n);
    C.x.push_back(n);
    D.x.push_back(n);
    D_C.x.push_back(n);

    B.y.push_back(result.B);
    U.y.push_back(result.U);
    C.y.push_back(result.C);
    D.y.push_back(result.D);
    D_C.y.push_back(result.D_C);
}

void full_simulation_results::calculate_avg(int tries)
{
    for(int i = 0; i < B.x.size(); i += tries)
    {
        int sum = std::accumulate(B.y.begin() + i, B.y.begin() + i + tries, 0);

        avg_B.x.push_back(B.x.at(i));
        avg_B.y.push_back(sum / (double)tries);


        sum = std::accumulate(U.y.begin() + i, U.y.begin() + i + tries, 0);

        avg_U.x.push_back(U.x.at(i));
        avg_U.y.push_back(sum / (double)tries);


        sum = std::accumulate(C.y.begin() + i, C.y.begin() + i + tries, 0);

        avg_C.x.push_back(C.x.at(i));
        avg_C.y.push_back(sum / (double)tries);


        sum = std::accumulate(D.y.begin() + i, D.y.begin() + i + tries, 0);

        avg_D.x.push_back(D.x.at(i));
        avg_D.y.push_back(sum / (double)tries);

        sum = std::accumulate(D_C.y.begin() + i, D_C.y.begin() + i + tries, 0);

        avg_D_C.x.push_back(D_C.x.at(i));
        avg_D_C.y.push_back(sum / (double)tries);
    }
}
