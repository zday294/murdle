# Murdle for Desktop

[Click here to visit the original Murdle website](https://murdle.com/)

## What is Murdle?

Murdle is a logic game created by G. T. Karber. The premise is simple: someone has been murdered somewhere with something. You, Detective Logico, must use your deduction skills to figure out who, where, and with what based on the clues given to you. It's like ClueTM but you can play by yourself. 

## Why are you making this?

In addition to [his website where you can play daily puzzles](https://murdle.com/), Karber has published book versions of his game including such things as plot. It sounds pretty neat! There's just one problem.

**The book is a terrible format for *Murdle*.**
 
Yeah, bold statement to make as someone who's not gotten through even the first book's easy section. I have my reasons, but before expounding on them, I should establish my premise. *Murdle: Volume 1* (or any other book in the series) is two things: a game and a story. The game is the process of taking the clues and correctly identifying the who, what, and where. The story is... well, a story - a series of events where characters do things. Yeah, story is usually more complicated than that - themes and all - but for our purposes this is the essential concept. *Murdle* tries to do both and technically succeeds, but does not do so well, and that's for three main reasons.  

1. **Checking the back of the book for answers interrupts the flow of reading.** If you're trying to enjoy a story, usually you want it delivered in the sequence of the medium. In a film, scene 3 plays immediately after scene 2. In a video game, level 5 begins after level 4. In a comic, panel 2 is to the right of panel 1. Yes, you can play around with these things and how they tell your story - scene 3 could be a flashback, level 5 may actually be optional, panel 2 may have elements that extend into the previous panel - but in general, each medium has a conventional way of ordering its information so that the audience can process the information in the correct order. *Murdle*, due to being a game as well as a book, breaks the conventional sequence and puts the "next page" at the back of the book. The reveal of a given murder's killer is up to hundreds of pages after its solving. Now obviously you aren't intended to read through all the other murders before your answers are confirmed; some of the murders lead into the one after them. You're supposed to jump to the back of the book and check your answers before moving on. But that's exactly my problem: stories in books really aren't meant to jump around from one page to another way later. The conclusions to scenes (aka the cases) are all lumped together in the back. Which leads into my next point...  
2. **Presenting all the answers in one place makes it easy to see the answer to a puzzle you haven't attempted yet.** Readers are familiar with the problem where you're reading through an incredibly tense and dramatic scene and your eyes jump to the end of the next page, and suddenly you've micro-spoiled the story for yourself. Now imagine you do that but for the next couple chapters. That would suck right? Of course it would. But that's the risk with *Murdle* which puts chapters worth of reveals all in one place. Sure, that's their solution to keeping solutions separate from the puzzles, and it technically functions for that purpose, but that doesn't mean it's the best solution.  
3. **Taking notes on deductions requires sacrificing replayability or accessibility.** *Murdle* includes a deduction matrix for players/readers to record the facts they've learned from the clues. This is a great feature which makes figuring out whodunnit a breeze once you've gotten a handle interpreting the clues. The problem is that to utilize the matrix you need to either write in the book or make a copy somewhere and write on the copy. With the former, you damage the replayability of the game. With the latter, you lessen the accessibility. Neither is great.   

By turning the book into a game, all these problems are solved. The solutions could be presented at the end of their respective puzzles, and the deduction matrix can both save your progress to come back to later and reset itself when playing through again (or letting someone else try it). Why do I believe a computerized version fixes those issues? Because the web version already does.  

## What's the plan here?

More than likely this will just be a prototype that demonstrates the idea could work mechanically. Additionally, it's a learning opportunity for me, a way for me to get better at making a GUI app and learn the JavaFX framework.

On a technical level, this is a JavaFX application. I doubt this to be an ideal solution, but I'm unsure what a better solution would be. Given that web version functions as a typical web page, I don't see the necessity of something like a game engine. So JavaFX it is.

The rough plan is to start by getting the main gameplay window working followed by a main menu and a chapter select. For chapters, I'll make up a couple examples. As a personal build, I may put the full first book into the game. After that, who knows? Maybe putting in effort to make things look nice or reaching out to the man himself and seeing about developing and publishing the thing properly. Only time will tell. 
