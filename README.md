Stats:
- 2023:
  - Live: 24 Stars (Python 3.10)
- 2024:
  - Training: 14 Stars (Java 17)
  - Live: 17 Stars (Scala 3.6)

Total: 55 Stars

Jan 2025 Retro Plans:
1. Research other approaches to (2024) day 9, mine is taking way too long to design and code

<details>
<summary>2023</summary>

First time participating, using it as a way to improve my Python skills.

Problems are in folders named by the day e.g. 1 is December 1st.

Part one has a topical label based on the story e.g. `oasis.py` and part two is `oasis2.py`

Provided test data in `test.txt` and actual data in `input.txt`
Can run problems like following: 
```bash
python oasis.py input.txt
```
Can run pytest in folders with test files (.py that start with test), in those folders `test.txt` is used if no name is provided.

1-10 inclusive are finished as well as day 16 and part 1 for days 14 and 15 for a total of 24 stars.
![2023 Progress Graph](2023/graph.png)

Environment Setup Instructions (note: python is an alias for python3):
```bash
cd 2023
python -m venv advent
source advent/bin/activate
pip install pydantic
pip install pytest
```
</details>

<details>
<summary>2024</summary>

<details>
<summary>Training</summary>

### advent-2022

Going to start training for 2024 by doing 2022 problems.

This time I will be using a different approach, instead of Python I'll be using it as a way to learn Java 17 and Spring Boot.

Additionally, I'll be solving all the problems in a single project.

Finally, instead of running the problems from the command line I'll be using Swagger UI to run the problems via API endpoints.

Project is maven based and was created using the following command:
```bash
mvn archetype:generate -DgroupId=com.advent.app -DartifactId=advent-2022 -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeVersion=1.4 -DinteractiveMode=false
```

### Progress

So far 1-7 inclusive are finished for a total of 14 stars.

### Environment Requirements
- Java 17
- Maven 3.8.4

### Running the Project
```bash
cd 2024/Training/advent-2022
mvn clean install
mvn spring-boot:run
```

### Running the Problems

Go to http://localhost:8080/swagger-ui/index.html and use the endpoints to run the problems

Can find the OpenAPI descriptions at http://localhost:8080/v3/api-docs

### Adding a New Problem

#### Adding Test Data

Create files in `src/main/resources` with the following format:
- `${day}test${numb}.txt` for the input data e.g. `1test1.txt`
- Add a newline to the beginning of the file containing the answer

#### Adding the Problem Input

Create a new file in `src/main/resources` with the following format:
- `${day}input.txt` e.g. `1input.txt`

#### Adding the Problem Solution

Create a new class in `src/main/java/com/advent/app` with the following format:
- A constructor that takes in ArrayList<String> as the input
- A method that returns the answer

Then add a new case to the day switch in Problem.java that sets output to the result of the solution method (as a string).

#### Adding Part 2

Just add the character b (lowercase) to the end of the day number e.g. `1b` for day 1 part 2. This applies to the test and input files as well as the Problem switch. The solution can just be another method in the same class.
</details>

<details>
<summary>Live</summary>

This year going to use Advent of Code as a way of improving my Scala skills!

Problems are in folders named by the day starting with 0 which contains template CookieCutter.scala I'll add to with code I intend to reuse across days along with notes and example.

### Progress

So far 1-4 inclusive are finished for a total of 8 stars.

### Environment Requirements
- Java 17
- Scala 3.6

### Running the Problem
```bash
cd 2024/Live/0
scala CookieCutter.scala
```

### Part 1 vs 2
This year I'm trying to just use the same file and have it print both answers. For day 1 it was easy to do side by side. Further down the line I'll likely have to move much of the logic into functions to keep things organized.

</details>

</details>