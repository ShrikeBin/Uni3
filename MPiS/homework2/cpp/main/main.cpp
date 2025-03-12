#include <simulation.hpp>
#include <plot.hpp>

#include <string>
#include <cmath>

axis<double> calc_average(const axis<double>& original, size_t block_size = 50)
{
    axis<double> avg_result;
    
    size_t num_blocks = original.y.size() / block_size;

    for (size_t block = 0; block < num_blocks; ++block) 
    {
        double x_avg = 0.0;
        double y_avg = 0.0;
        
        for (size_t i = 0; i < block_size; ++i) 
        {
            size_t idx = (block * block_size) + i;

            if (idx < original.x.size()) 
            {
                x_avg += original.x[idx];
                y_avg += original.y[idx];
            }
        }

        avg_result.x.push_back(x_avg / block_size);
        avg_result.y.push_back(y_avg / block_size);
    }

    return avg_result;
}


int main()
{
    std::string file_base = std::string(OUTPUT_DIRECTORY) + "Plot";

    full_simulation_results full_results = full_simulate(50);

    plot(full_results.B, full_results.avg_B, file_base + "B.png", "First collision");
    plot(full_results.U, full_results.avg_U, file_base + "U.png", "Empty bins after n balls");
    plot(full_results.C, full_results.avg_C, file_base + "C.png", "All bins have at least one ball");
    plot(full_results.D, full_results.avg_D, file_base + "D.png", "All bins have at least two balls");
    plot(full_results.D_C, full_results.avg_D_C, file_base + "DC.png", "D - C");

    //--------------------------------------
    
    axis<double> TEMP = full_results.B.apply_to_y([&full_results](double y) 
    {
        static size_t i = 0;
        double x = full_results.B.x[i];
        i++;
        return (x != 0) ? y / x : 0.0;
    });
    plot(TEMP, calc_average(TEMP, 50), file_base + "BbyN.png", "B(n) / n");

    TEMP = full_results.B.apply_to_y([&full_results](double y) 
    {
        static size_t i = 0;
        double x = full_results.B.x[i];
        i++;
        return (x != 0) ? y / std::sqrt(x) : 0.0;
    });
    plot(TEMP, calc_average(TEMP, 50), file_base + "BbysqrtN.png", "B(n) / sqrt(n)");

    //--------------------------------------

    TEMP = full_results.U.apply_to_y([&full_results](double y) 
    {
        static size_t i = 0;
        double x = full_results.B.x[i];
        i++;
        return (x != 0) ? y / x : 0.0;
    });
    plot(TEMP, calc_average(TEMP, 50), file_base + "UbyN.png", "U(n) / n");

    //--------------------------------------

    TEMP = full_results.C.apply_to_y([&full_results](double y) 
    {
        static size_t i = 0;
        double x = full_results.B.x[i];
        i++;
        return (x != 0) ? y / x : 0.0;
    });
    plot(TEMP, calc_average(TEMP, 50), file_base + "CbyN.png", "C(n) / n");

    TEMP = full_results.C.apply_to_y([&full_results](double y) 
    {
        static size_t i = 0;
        double x = full_results.B.x[i];
        i++;
        return (x != 0) ? y / std::pow(x,2) : 0.0;
    });
    plot(TEMP, calc_average(TEMP, 50), file_base + "CbyN2.png", "C(n) / n^2");

    TEMP = full_results.C.apply_to_y([&full_results](double y) 
    {
        static size_t i = 0;
        double x = full_results.B.x[i];
        i++;
        return (x != 0) ? y / (x*std::log(x)) : 0.0;
    });
    plot(TEMP, calc_average(TEMP, 50),  file_base + "CbyNlog.png", "C(n) / nln(n)");

    //--------------------------------------

    TEMP = full_results.D.apply_to_y([&full_results](double y) 
    {
        static size_t i = 0;
        double x = full_results.B.x[i];
        i++;
        return (x != 0) ? y / x : 0.0;
    });
    plot(TEMP, calc_average(TEMP, 50), file_base + "DbyN.png", "D(n) / n");

    TEMP = full_results.D.apply_to_y([&full_results](double y) 
    {
        static size_t i = 0;
        double x = full_results.B.x[i];
        i++;
        return (x != 0) ? y / std::pow(x,2) : 0.0;
    });
    plot(TEMP, calc_average(TEMP, 50), file_base + "DbyN2.png", "D(n) / n^2");

    TEMP = full_results.D.apply_to_y([&full_results](double y) 
    {
        static size_t i = 0;
        double x = full_results.B.x[i];
        i++;
        return (x != 0) ? y / (x*std::log(x)) : 0.0;
    });
    plot(TEMP, calc_average(TEMP, 50), file_base + "DbyNlog.png", "D(n) / nln(n)");

    //--------------------------------------

    TEMP = full_results.D_C.apply_to_y([&full_results](double y) 
    {
        static size_t i = 0;
        double x = full_results.B.x[i];
        i++;
        return (x != 0) ? y / x : 0.0;
    });
    plot(TEMP, calc_average(TEMP, 50), file_base + "DCbyN.png", "D(n) - C(n) / n");

    TEMP = full_results.D_C.apply_to_y([&full_results](double y) 
    {
        static size_t i = 0;
        double x = full_results.B.x[i];
        i++;
        return (x != 0) ? y / (x*std::log(x)) : 0.0;
    });
    plot(TEMP, calc_average(TEMP, 50), file_base + "DCbyNlog.png", "D(n) - C(N) / nln(n)");

    TEMP = full_results.D_C.apply_to_y([&full_results](double y) 
    {
        static size_t i = 0;
        double x = full_results.B.x[i];
        i++;
        return (x != 0) ? y / (x*std::log(std::log(x))) : 0.0;
    });
    plot(TEMP, calc_average(TEMP, 50), file_base + "DCbyNloglog.png", "D(n) - C(n) / nlnln(n)");

    return 0;
}
