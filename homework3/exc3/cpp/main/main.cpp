#include <simulation.hpp>
#include <plot.hpp>
#include <stations.hpp>

#include <string>
#include <algorithm>
#include <cmath>

void process_results(full_simulation_results& results, const std::string& file_base, double p);

int main()
{
    {
        double p = 0.5;
        std::string file_base = std::string(OUTPUT_DIRECTORY) + "Plot_.5_";
        full_simulation_results results = full_simulate_broadcast(50, p);
        process_results(results, file_base, p);
    }

    {
        double p = 0.1;
        std::string file_base = std::string(OUTPUT_DIRECTORY) + "Plot_.1_";
        full_simulation_results results = full_simulate_broadcast(50, p);
        process_results(results, file_base, p);
    }
}

void process_results(full_simulation_results& results, const std::string& file_base, double p)
{
    plot(results.T, results.avg_T, file_base + "T.png", "T");
}
