# Shunting-Yard Calculator
My first Android App!😋

Scientific calculator for Android based on Shunting Yard, supporting basic scientific calculation.

# Features
- support bracket
- trigonometric function
- exponential operation
- logarithm operation
# Screeshot
![](https://raw.githubusercontent.com/zdong1995/PicGo/master/img/Calculator.001.JPEG)

# Shunting Yard Algorithm
The shunting yard algorithm is used to parse input in infix notation, while respecting the order of operations:

> 1 * 2 + 3 * 4

The fixity of arithmetic operations dictates that `1*2` and `3*4` be computed before `2+3`, but a standard stack-based parser would want to interpret operations in the order they appear (`1*2`, `3+3`, `6*4`).

**Parsing to Reverse Polish Notation**

One answer is to parse infix notation to an intermediate notation that can be parsed in-order. Post-fix notation (also called Reverse Polish Notation) is an example of such a format; rather than putting the operations between the operands, we put them after the operands:

>1 2 * 3 4 * +

# Reference
https://en.wikipedia.org/wiki/Shunting-yard_algorithm
