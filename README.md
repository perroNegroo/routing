# Routing Network Simulator

A Java 17 command-line application for modelling IPv4 subnetworks, managing network topologies, and calculating shortest packet routes between systems.

The simulator loads a network from a Mermaid-style text file, validates its structure, and supports interactive commands for inspecting and modifying the topology. Weighted links are used inside subnets, while router-to-router links are treated as unweighted connections.

## Features

- Load and validate multiple non-overlapping IPv4 subnets in CIDR notation.
- Model computers, routers, weighted intra-subnet links, and unweighted inter-router links.
- Calculate shortest paths inside a subnet with Dijkstra's algorithm.
- Calculate minimum-hop routes between routers with breadth-first search (BFS).
- Add or remove computers and network connections at runtime.
- List subnets, address ranges, and systems in sorted order.
- Print the complete hop-by-hop route of a simulated packet.
- Continue processing commands after invalid input by returning descriptive error messages.

## Requirements

- Java Development Kit (JDK) 17
- Apache Maven

The project has no external runtime dependencies.

## Build and Run

From the project root, compile the application with Maven:

```bash
mvn clean compile
```

Start the command-line interface:

```bash
java -cp target/classes routing.Main
```

The application reads commands from standard input and runs until `quit` is entered.

## Network File Format

Networks are defined in a Mermaid-style text format. Each subnet contains exactly one router, any number of computers, and weighted bidirectional connections. Router-to-router connections are declared after the subnet blocks and do not have weights. Router identifiers must follow the `<name>_Router` pattern, while computer identifiers must follow `<name>_PC<number>`.

```text
graph
subgraph 192.168.1.0/24
    A_Router[192.168.1.1]
    A_PC1[192.168.1.2]
    A_PC2[192.168.1.3]
    A_Router <-->|7| A_PC1
    A_Router <-->|2| A_PC2
end
subgraph 10.0.0.0/16
    B_Router[10.0.0.1]
    B_PC1[10.0.0.2]
    B_Router <-->|6| B_PC1
end
A_Router <--> B_Router
```

### Validation Rules

A loaded network must satisfy the following conditions:

- Subnet address ranges must not overlap.
- Every system must belong to its declared subnet.
- Each subnet must contain exactly one router.
- The router must use the first usable IP address of its subnet.
- Connections within a subnet must have a positive weight.
- Connections between subnets may only connect routers and must not have a weight.
- System IP addresses and existing connections must be unique where applicable.

When a valid file is loaded, its contents are printed exactly as read and the active network is replaced.

## Commands

| Command | Description |
|---|---|
| `load network <path>` | Load and validate a network definition file. |
| `list subnets` | Print all subnet identifiers sorted by network address. |
| `list range <subnet>` | Print the first and last IP address of a subnet. |
| `list systems <subnet>` | Print all systems in a subnet sorted by IP address. |
| `add computer <subnet> <ip>` | Add a computer to an existing subnet. |
| `remove computer <subnet> <ip>` | Remove a computer from a subnet. Routers cannot be removed. |
| `add connection <ip> <ip> <weight>` | Add a weighted connection between two systems in the same subnet. |
| `add connection <router-ip> <router-ip>` | Add an unweighted connection between routers in different subnets. |
| `remove connection <ip> <ip>` | Remove an existing intra-subnet or inter-router connection. |
| `send packet <source-ip> <destination-ip>` | Print the shortest route, including the sender and receiver. |
| `quit` | Stop the application. |

A network must be loaded before topology-dependent commands are used.

## Example Session

```text
load network ./input/example_graph.txt
list subnets
10.0.0.0/16 192.168.1.0/24

list range 10.0.0.0/16
10.0.0.0 10.0.255.255

list systems 192.168.1.0/24
192.168.1.1 192.168.1.2 192.168.1.3

send packet 192.168.1.2 10.0.0.2
192.168.1.2 192.168.1.1 10.0.0.1 10.0.0.2

quit
```

## Routing Strategy

### Within a Subnet

Intra-subnet connections are weighted. The application runs Dijkstra's algorithm from every node to precompute the lowest-cost paths within each subnet.

### Between Subnets

Inter-router connections are unweighted. BFS is used to find routes with the fewest router hops. For communication between different subnets, the complete route is assembled from:

1. the shortest path from the source system to its router;
2. the minimum-hop path between the source and destination routers;
3. the shortest path from the destination router to the destination system.

## Project Structure

```text
src/routing/
├── Main.java                         # Application entry point
├── programm/
│   ├── CommandHandler.java           # Interactive command dispatcher
│   └── commands/                     # Command implementations
└── model/
    ├── graphmodel/
    │   ├── GraphManager.java         # Stores and updates loaded subnets
    │   ├── graph/                    # Subnets, nodes, routers, and edges
    │   └── utils/                    # Routing, CIDR, sorting, and validation utilities
    └── txtmanager/                   # File loading and topology extraction
```

## Error Handling

Invalid commands, arguments, files, or topology changes produce a single-line message beginning with `Error,`. The program then continues waiting for the next command.

## Educational Context

This project was developed as a programming assignment focused on object-oriented modelling, graph algorithms, IPv4 subnet handling, file parsing, and command-line application design.
