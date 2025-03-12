#ifndef AXIS_HPP
#define AXIS_HPP

#include <vector>
#include <functional>

template<typename T>
struct axis
{
    std::vector<T> x;
    std::vector<T> y;

    // Conversion
    template<typename U>
    operator axis<U>()
    {
        return axis<U>
        {
            std::vector<U>(x.begin(), x.end()),
            std::vector<U>(y.begin(), y.end())
        };
    }

    // Apply a function to all elements in x (returns a copy)
    axis apply_to_x(const std::function<T(T)>& func) const
    {
        axis<T> result = *this;  // Make a copy of the current object
        for (auto& val : result.x)
        {
            val = func(val);
        }
        return result;  // Return the modified copy
    }

    // Apply a function to all elements in y (returns a copy)
    axis apply_to_y(const std::function<T(T)>& func) const
    {
        axis<T> result = *this;  // Make a copy of the current object
        for (auto& val : result.y)
        {
            val = func(val);
        }
        return result;  // Return the modified copy
    }
};

#endif
