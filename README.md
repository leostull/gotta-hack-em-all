Team Gotta Hack 'Em All's entry for the Blasterhacks Hackathon 2025
## Inspiration

When brainstorming ideas for our project, we wanted to ensure our work is meaningful and capable of solving a real issue. Each member of our group has dietary restrictions. These restrictions make it challenging to ensure various foods meet our needs. Therefore, we developed this app to help clarify the ingredients and possible allergens within various foods.

## What it does

Our mobile app utilizes a phone’s camera to scan the barcode of a give food. By concept: the barcode is searched online to find information about the food product and return a list of ingredients that are flagged as allergens. By the 36 hour reality: the barcode is searched on an imported dataset to find information about the food product and return a list of ingredients that are flagged as allergens.

## How we built it

We employed android studio to run our java-based Groq API app on an android phone. We worked with a variety of java and android libraries including JSON and Bitmaps. We used Google MlKit when scanning the barcode.

## Challenges we ran into

Our challenges can be broken down into two topics: the scanner and the API key. The scanner was challenging because of the mapping quality of the image and the specifications on size necessary for the barcode to properly scan. The API key was challenging when initially getting it to run with the scanner. We also found it challenging to create different threads to ensure the API key could look up information while the UI/UX was running with the scanner.

## Accomplishments that we're proud of

We are proud to have completed a functional barcode scanner and being to implement the functional ingredient lookup and allergen sorting. We were very proud to have fixed the threading issue with the API and the scanner. Overall, we are proud with the progress we have made, especially for this being the first hackathon for every member of the team.

## What we learned

We learned the importance of planning a project prior to doing it. Furthermore, we learned the importance of delegating tasks to each member based on the member’s given strengths or resources. From a technical perspective, the group learned about scanning and mapping with Bitmaps and Google MlKit. Two of the three group members also learned how to program in Java.

## What's next for Allergen Barcode Scanner

Give the proper resources, the team would love to pair our Groq API with another API key that enables our app to read data live from the internet.
