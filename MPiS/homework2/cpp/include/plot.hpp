#ifndef PLOT_HPP
#define PLOT_HPP

#include <simulation.hpp>

#include <string>

void plot(const axis<double> &result, const axis<double> &avg_result, const std::string &file, const std::string &title);

#endif