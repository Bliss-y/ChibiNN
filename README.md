# ChibiNN

Basically trying to  copy pytorch into java for personal exercise. 

Goals:
  1. create tensor with ability to multiply, add, exp etc. with ability to back prop
  2. create different layers of neural network . (atleast linear and tanh)
  3. create a basic training setup and backprop system.

Current goals:
   1. Implement broadcasting of tensors. // semi complete.
   2. Implement backward functions in tensor operation (multiply and add)
   3. Implement zero grad -> semi complete works for now !;
   4. Implement auto grad. --> calculating a grad and doing backward on parent is a bad idea! find a wayt to only backward when all it's child has done the gradiation on it.

