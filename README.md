# Advent of Code Submit plugin

Effortlessly solve, build, and submit your Advent of Code solutions directly from IntelliJ IDEA!

## 🎯 Purpose

<!-- Plugin description -->
This project is a JetBrains IntelliJ plugin designed to streamline the Advent of Code experience for Kotlin and Java enthusiasts.

With this plugin, you can:

- automatically build and run your solution for any day of the event.
- submit your answers directly to Advent of Code without leaving your IDE.

Whether you're a competitive solver or just enjoying the holiday coding fun, this plugin saves you time and lets you focus on solving puzzles.

To submit your solutions, you need to log in into your Advent of Code account using the `AOC` tool window. The plugin will store your session cookie in the IDE's secure storage, so you don't have to log in every time you want to submit an answer.

At the moment, the plugin can only submit Java or Kotlin solutions. If you're using another language, you can still use the plugin to view the puzzles and download the inputs, but you'll need to submit your answers manually.

The inputs are downloaded into the `.aoc` folder in your project root. The plugin will automatically create this folder if it doesn't exist.
<!-- Plugin description end -->

## 📝 TODO list

- [x] View the Advent of code puzzles
- [x] Submit your answers directly to Advent of Code
- [x] Download the inputs

## 👏 Kudos

Special kudos to this repo: [Advent Of Code](https://github.com/UnderKoen/AdventOfCodePlugin).
It helped me a lot to build the basis of this plugin.
The complete tree view of the puzzles is based on its work.

## Installation

- Using the IDE built-in plugin system:
  
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > <kbd>Search for "advent-of-code-submit-plugin"</kbd> >
  <kbd>Install</kbd>
  
- Using JetBrains Marketplace:

  Go to [JetBrains Marketplace](https://plugins.jetbrains.com/plugin/MARKETPLACE_ID) and install it by clicking the <kbd>Install to ...</kbd> button in case your IDE is running.

  You can also download the [latest release](https://plugins.jetbrains.com/plugin/MARKETPLACE_ID/versions) from JetBrains Marketplace and install it manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>

- Manually:

  Download the [latest release](https://github.com/ojacquemart/advent-of-code-submit-plugin/releases/latest) and install it manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>

---
Plugin based on the [IntelliJ Platform Plugin Template][template].

[template]: https://github.com/JetBrains/intellij-platform-plugin-template
[docs:plugin-description]: https://plugins.jetbrains.com/docs/intellij/plugin-user-experience.html#plugin-description-and-presentation
