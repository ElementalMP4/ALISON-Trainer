# ALISON Trainer
A tool to create models for ALISON from text files

## How to use ALISON Trainer

1) Pull and build the code from this repository (or use the latest release)
2) Run `java -jar AlisonTrainer-0.0.1.jar [input file]`

Note: The input file MUST be a text file with each line split by `\n`

Once the new model has been created, the result can be found ni a newly created `output` folder.

Copy the `words.alison` file into your ALISON instance `models` directory (inside of a named folder)

If you would like the new model to have an icon and a custom name when being used via the `imitate` command, a `meta.json` file can be added to the model directory.

Example:

```json
{
    "name": "A cool custom model",
    "iconUrl": "https://www.savacations.com/wp-content/uploads/2021/02/Blog-Capybara-Pantanal-Brazil3.jpg"
}
```