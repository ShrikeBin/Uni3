#include <simulation.hpp>
#include <plot.hpp>

#include <string>
#include <algorithm>
#include <cmath>


int main()
{
    std::string file_base = std::string(OUTPUT_DIRECTORY) + "Plot_";
    full_simulation_results results = full_simulate_sort(50);

    plot(results.cmp, results.avg_cmp, file_base + "cmp.png", "Average compare amount");
    plot(results.s, results.avg_s, file_base + "s.png", "Key move average");

    { // c) cmp(n)/n
        axis<double> result;
        result.x = results.avg_cmp.x;
        std::transform(
            results.avg_cmp.y.begin(),
            results.avg_cmp.y.end(),
            results.avg_cmp.x.begin(),
            std::back_inserter(result.y),
            [](auto& c, auto& n){return c / n;}
        );

        plot_line(result, file_base + "c)-1.png", "cmp(n) / n");
    }

    { // c) cmp(n)/n^2
        axis<double> result;
        result.x = results.avg_cmp.x;
        std::transform(
            results.avg_cmp.y.begin(),
            results.avg_cmp.y.end(),
            results.avg_cmp.x.begin(),
            std::back_inserter(result.y),
            [](auto& c, auto& n){return c / (n*n);}
        );

        plot_line(result, file_base + "c)-2.png", "cmp(n) / n^2");
    }

    { // d) s(n)/n
        axis<double> result;
        result.x = results.avg_s.x;
        std::transform(
            results.avg_s.y.begin(),
            results.avg_s.y.end(),
            results.avg_s.x.begin(),
            std::back_inserter(result.y),
            [](auto& s, auto& n){return s / n;}
        );

        plot_line(result, file_base + "d)-1.png", "s(n) / n");
    }

    { // d) s(n)/n^2
        axis<double> result;
        result.x = results.avg_s.x;
        std::transform(
            results.avg_s.y.begin(),
            results.avg_s.y.end(),
            results.avg_s.x.begin(),
            std::back_inserter(result.y),
            [](auto& s, auto& n){return s / (n*n);}
        );

        plot_line(result, file_base + "d)-2.png", "s(n) / n^2");
    }
}
