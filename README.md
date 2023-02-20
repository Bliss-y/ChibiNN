# ChibiNN

Basically trying to  copy pytorch into java for personal exercise. 
start date: 17 feb

Goals:
  1. create tensor with ability to multiply, add, exp etc. with ability to back prop
  2. create different layers of neural network . (atleast linear and tanh)
  3. create a basic training setup and backprop system.

Current goals:
   1. Implement broadcasting of tensors. // semi complete.
   2. Implement backward functions in tensor operation (multiply and add)
   3. Implement zero grad -> semi complete works for now !; -> complete!
   4. Implement auto grad. --> complete! 

Goals for feb 21:
1. implement the requires_grad properly. check in multiplication and addition so the gradiation is not implemented while backpropping!
    maybe use static variable to check if we're backpropping..? so the gradFunc calculation is not done
2. Start Creating linear and tanh layers! they no longer need to hold gradiation!