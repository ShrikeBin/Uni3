#include <plot.hpp>
#include <simulation.hpp>
#include <axis.hpp>

#include <matplot/matplot.h>

#include <string>
#include <vector>

void plot(const axis<double> &result, const axis<double> &avg_result, const std::string &file, const std::string &title)
{
    auto y_lim = matplot::gca()->y2lim();
    y_lim.at(0) = 0;
    matplot::ylim(y_lim);

    matplot::scatter(result.x, result.y)->marker(".").marker_size(0.5).color("blue"); //points
    matplot::hold(matplot::on);

    matplot::plot(avg_result.x, avg_result.y)->line_width(1).color("red"); //average line

    matplot::title(title);
    matplot::xlabel("N"); 
    matplot::ylabel("Result value"); 
    matplot::grid(true);

    matplot::hold(matplot::off);

    matplot::save(file);
}

void plot_line(const axis<double> &result, const std::string &file, const std::string &title)
{
    auto y_lim = matplot::gca()->y2lim();
    y_lim.at(0) = 0;
    matplot::ylim(y_lim);

    matplot::plot(result.x, result.y)->line_width(2).color("red");

    matplot::title(title);
    matplot::xlabel("N"); 
    matplot::ylabel("Result value"); 
    matplot::grid(true);

    matplot::save(file);
}