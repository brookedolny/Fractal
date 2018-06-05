# Fractal
This project draws Mandelbrot and Julia Sets of non-negative order. 

## Mandelbrot Sets
The Mandelbrot Set is a set of complex numbers `c` that, when iterated through the function `f(z) = z^2 + c`, remain bounded.
For example, `c = 1` is not in the Mandelbrot Set, since:
```
f(0) = 0^2 + 1 = 1
f(1) = 1^2 + 1 = 2
f(2) = 2^2 + 1 = 5
f(5) = 5^2 + 1 = 26
...
```
which clearly goes to infinity as the values of `z` continue to be iterated through this function.
Also, `c = -1` is in the Mandelbrot set, since:
```
f(0)  =  (0)^2 - 1 = -1
f(-1) = (-1)^2 - 1 = 0
f(0)  = (-1)
...
``` 
Which clearly alternates between 0 and 1, and so is bounded. This means that `c = -1` is in the Mandelbrot Set, and `c = 1` is not.

Although the most famous image of the Mandelbrot Set is the set with the iterated function as `f(z) = z^2 + c`, there is no reason why we cannot use different powers of `z`. 
This project generates Mandelbrot Set fractals for the general iterative polynomial `f(z) = z^d + c`, for any real number `d ≥ 0`.

The sets generated by these polynomials are chaotic in nature, and can be used to generate beautiful fractals. The fractals are generated by assigning a value of `c` to each pixel, and then iterating them through the appropriate polynomial `n` times, where `n` is sufficiently large (typically over 1000).

## Julia Sets
A Julia Set is defined similarly to the Mandelbrot Set, except the value of `c` is kept constant and the initial iterated value `z` is varied for each point in the complex plane. 

The Julia Set fractals are generated in a similar way to the Mandelbrot Set fractals, only the value of `z` varies over the image.