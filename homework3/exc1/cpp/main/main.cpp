#include <simulation.hpp>
#include <plot.hpp>

#include <string>
#include <algorithm>
#include <cmath>

void process_results(full_simulation_results& full_results, const std::string& file_base);

int main()
{
    {
        const int d = 1;
        std::string file_base = std::string(OUTPUT_DIRECTORY) + "Plot D(" + std::to_string(d) + ")";
        full_simulation_results results = full_simulate_ballanced(50, d);
        process_results(results, file_base);
    }

    {
        const int d = 2;
        std::string file_base = std::string(OUTPUT_DIRECTORY) + "Plot D(" + std::to_string(d) + ")";
        full_simulation_results results = full_simulate_ballanced(50, d);
        process_results(results, file_base);
    }
}

void process_results(full_simulation_results& full_results, const std::string& file_base)
{

    plot(full_results.L, full_results.avg_L, file_base + "L.png", "Maximum load");

    { // f) l(n) / (ln n / ln ln n)
        axis<double> result;
        result.x = full_results.avg_L.x;
        std::transform(
            full_results.avg_L.y.begin(),
            full_results.avg_L.y.end(),
            full_results.avg_L.x.begin(),
            std::back_inserter(result.y),
            [](auto& l, auto& n){return l / (std::log(n) / std::log(std::log(n)));}
        );

        plot_line(result, file_base + "Lbylnnlnln.png", "L(n) / (ln n / ln ln n)");
    }

    { // f) l(n) / (ln ln n / ln 2)
        axis<double> result;
        result.x = full_results.avg_L.x;
        std::transform(
            full_results.avg_L.y.begin(),
            full_results.avg_L.y.end(),
            full_results.avg_L.x.begin(),
            std::back_inserter(result.y),
            [](auto& l, auto& n){return l / (std::log(std::log(n)) / std::log(2));}
        );

        plot_line(result, file_base + "Lbylnlnnln2.png", "L(n) / (ln ln n / ln 2)");
    }
}