# SanYueQi

![SanYueQi interface](docs/Ui.png)

> “Are you here to play with me?”

This is a simple chatbot written in Java that can be used for daily task management.

For the eagle-eyed among you, yes, this project is themed around [*March 7th*](https://hsr.hoyoverse.com/en-us/character?utm_source=hsrofficialweb&utm_medium=fab&utm_campaign=button&worldIndex=3&charIndex=3), one of the main heroines of *Honkai: Star Rail*. She's here to play because ~~your mental health after CS3230~~ the *Astral Express* broke down.

March can help you manage:

- Todos
- Deadlines
- Events
- Completed tasks
- Formatted dates
- Protection from *Silver Wolf*'s hacks (coming soon™)

## Main commands

1. Add a task using `todo`, `deadline`, or `event`.
2. View tasks using `list`.
3. Mark a task using `mark` or `unmark`.
4. Delete tasks using `delete`.
5. Search for tasks using `find`.

## Features

- [x] Management of various task types
- [x] Flexible symbols, because nobody likes bugs caused by delimiters. You can use pretty much any symbol you like in your task description.

Example command:

```java
event Cooking * Show | The Herta's \ Magic / Kitchen | Please, "RSVP" by the 'deadline'. /from 2026-09-10 /to whenever I'm done baking a cake
```

Run it with `./gradlew run`.

## How to run
TODO

## Credit

[@LazeyLazed](https://x.com/LazeyLazed) for the illustration of March 7th used in the chatbot

[Ahmad Ihsan](https://www.dafont.com/profile.php?user=1228713) for the Felleria font used in the chatbot
