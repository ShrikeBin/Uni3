#ifndef STATIONS_HPP
#define STATIONS_HPP

#include <simulation.hpp>

#include <vector>
#include <random>


class Receiver
{
private:
    static std::uniform_real_distribution<double> dist;
    static std::mt19937 rng;

    double p;

public:
    Receiver(double p);

    bool receive();
};

class Broadcaster
{
private:
    std::vector<Receiver> receivers;

    int non_received;

public:
    single_simulation_results results;

public:
    Broadcaster(int n, double p);

    bool broadcast();
};

#endif