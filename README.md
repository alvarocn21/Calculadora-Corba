# Calculator-CORBA
Calculadora basica utilizando la arquitectura CORBA
Las funciones que tiene esta calculador son:
  - Sumar
  - Restar
  - Multiplicar
  - Dividir
  - Obtener el resto de una división

Las instrucciones para las operaciones son:
  - Primero poner la operacion (+, -, /, *, R)
  - Primer numero 
  - Segundo numero

Ejemplo para sacar el resto:

R 40 3

Resultado: 1


## Como compilarla
### Lado del Servidor
idlj Calc.idl
javac StartServer.java

### Lado del Cliente
javac StartClient.java

## Como correrlo
Se necesita tener abiertos 3 terminales a la vez

En el primer terminal, en el lado del servidor hay que poner:
orbd -ORBInitialPort 1050 -ORBInitialHost localhost

En el segundo terminal, en el lado del servidor hay que poner:
java StartServer -ORBInitialPort 1050 -ORBInitialHost localhost

En el tercer terminal, en el lado del cliente hay que poner:
java StartClient -ORBInitialPort 1050 -ORBInitialHost localhost
