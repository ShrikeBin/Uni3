## Dependencies
1. **CMake**
   - A build system generator (version 3.15+ ).

2. **GNUplot**
   - ```sudo apt install gnuplot```

3. **Matplot++**
   - (included in the `/matplot` subdirectory), 
   - run ```git clone https://github.com/alandefreitas/matplotplusplus/``` in CMakeList.txt folder

## Running
```bash
mkdir build
cd build
cmake ..
cmake --build .
./bin/main
```