Miniredis spring boot application.

Run with jdk1.8

Project contains a simple controller to perform all commands requested. The path param is cmd followed with command and parameters required, separated with blank spaces.

If some command receives more or less parameters than required, api should throw a 400 ERROR with the specific error message.

Command classes are implementations of command interface, which is the parent class, followed by an abstract class per command type (Sorted set command, String commands, server commands) and then the specific command that performs the final command. This implementation follows the Open close principle and single responsability and interface segregation.

I've put redis 'repository' in a repository folder, but this is arguable, because this is not a third party class or infrastructure, could be in domain folder, inside the 'hexagon'. It's framework agnostic, so it's highly decoupled.

