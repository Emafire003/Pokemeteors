<img width="1013" height="128" style="vertical-align:middle" alt="pokemeteors_text" src="https://github.com/user-attachments/assets/bf329863-5b17-4854-8151-02f82ef53c72" />

#
A mod that allows space-adjacent (or normal!) cobblemon pokémons to spawn inside a falling meteorite, 
courtesy of the OhMyMeteors mod!

<div align=center>
<img width="533" height="300" style="vertical-align:middle" alt="miniortake160fps1080p-ezgif com-optimize" src="https://github.com/user-attachments/assets/727f13e7-e21d-41f3-84b1-dce242b1ee84" />
</div>

When a pokémon normally spawns, the mod checks if it should spawn inside a meteor (configurable with datapacks), and with which chance. Then, a falling meteor
spanws in the sky above the pokémon's spawn position, creating an impact crater along with a meteor and the pokémon itself. The pokémon follows all of the normal
spawn rules you can define with Cobblemon's datapacks, so for example if it should have spawned only in cold biomes, it will spawn only in cold biomes, if it was supposed
to be of a level between 20 and 30, it will have a level between 20 and 30 and so on. 

<img width="1013" height="128" alt="pokemeteors_text" href="https://nodecraft.com/r/emalightdev" src="https://github.com/user-attachments/assets/66b45404-d8c6-497b-8cc8-ac08adfae0de" />

The best way to enjoy this mod is with other people on a server, so you can use the code EMALIGHTDEV on [Nodecraft](https://nodecraft.com/r/emalightdev) for discount!

## Features
Pokemons can spawn with different meteor structures of different size classes, they can also be unique and be specific for some
of their aspects (for example Minior's core color)

//insert meteor structures examples, maybe a collage?//

### Default spawns
By default the mod adds meteor spawns for:
- Minior: small meteors, each core color has a unique one, always spawns as a meteor
- Clefable: medium meteors, 1/8 chance of spawning inside a meteor
- Clefairy: small meteors, 1/6 chance of spawning inside a meteor
- Cleffa: same as clefairy
- Lunatone: medium meteors, has its unique meteor, 1/3 chance of spawning inside a meteor
- Solrock: same as lunatone (has its own unique meteor)
- Deoxys: big meteor, has its own unique meteor, always spawns in a meteor
- Solgaleo: big meteor, they spawn directly without a meteor structure, always spawns insde a meteor
- Lunala: same as solgaleo
- Necrozma: same as solgaleo and lunala
- Kyurem: huge meteor, has its own icy meteor, always spawns as a meteor (yes I know, technically it should have been the original dragon)

### Customize spawns (datapack)
You can customize which pokémon spawn with meteors, what are the chance, and the meteor structure that they spawn in.
//TODO add a default datapack example

To do this, you must create a json file inside `data/pokemeteors/spawns/<yourfilename>.json`
The structure inside the file should be as it follows:

```json
{
  "speciesMeteorChances": [
    {
      "species": "cobblemon:<pokemonname>",
      "chance": <1 in x chances>,
      "max_meteor_size": <1-50>,
      "min_meteor_size": <1-50>,
      "meteor_size_class": "<small|medium|big|huge>",
      "unique_meteor": "",
      "aspect_unique_meteor": {}
    },
    {
      "species": "cobblemon:clefairy",
      "chance": 10,
      "max_meteor_size": 6,
      "min_meteor_size": 3,
      "meteor_size_class": "small",
      "unique_meteor": "",
      "aspect_unique_meteor": {}
    }
  ],
  "override_for": [],
  "version_do_not_touch": 1
}
```

Inside `specieMeteorChances` you can put pokémons that should spawn with a meteor.
Each pokemon-chance has a few parameters you have to fill and others which allow you to specify with which meteor structure it will spawn
- `species`: The id of the pokémon that should spawn with a meteor
- `chance`: The chance of this pokémon spawing with a meteor vs spawning normally, expressed as 1 in every <x> chance of this happening. (For example, setting it to `2` will make the pokemon spawn with a 50% chance, aka half of the times)
- `max_meteor_size`: A number between 1 and 50, represents how big the meteor can be at most. The bigger the meteor, the bigger the explosion. And the meteor entity that gets rendered in game
- `min_meteor_size`: Same as above, but the minimum size
- `meteor_size_class`: One of 4 possible values, `small`, `medium`, `big`, `huge`. This value is used to determine how deep the meteor structure will be embedded, and a rough description of how big the meteor is going to be. It also used to determine with which meteor structure the pokémon will spawn, in fact the to each class corresponds a set of meteor structure types
- `unique_meteor`: An option value, overrides the meteor structure selected by the meteor size class and instead spawns a specific structure, given its id. 
- `aspect_unique_meteor`: similar to the option above, it's a map of `aspect:structure_id`, and it will spawn a unique meteor structure for the given aspect. An example is Minior, with each core color spawning with its own meteor structure

`override_for` lets you specify overrides on specific pokémons, for example let's say you want to spawn a minior with your own structure, but the default datapack spawns with the default colored meteors. By putting `"cobblemon:minior"` in the list your config will override the default (or other) values. To override all of the default pokémon spawns you can add as a first element of the list `"all_default"`
### Customize meteor structures (datapack)
You can add your own structures for the meteors the pokémon spawn in. These have the same format of the vanilla StructureBlock `.nbt` files, and you can generated them the same way. You can also use worldedit and litematica by using the command provided by [OhMyMeteors](https://modrinth.com/mod/ohmymeteors) (`/omm custom`), check that modpage for more info. 
You can place those files inside `data/pokemeteors/structure/`. Place unique structures that you don't want to spawn normally but just for some kind of pokemon in the root folder (<-- that one), and other meteors in the folder corresponding to their size category, like `small` for small meteors etc. Inside the size folders you can also have a 'special' folder, like so: `data/pokemeteors/structure/small/special/`. A structure placed in the special folder will have a rarer (10%) chance of appearing instead of the normal ones. You can configure this chance in OhMyMeteor's config
If you want to get rid of a specific structure added by default by this mod you can add a file in the corresponding folder with the same name but prefixed with "ignore_". For example, if you don't want to spawn the `solmeteor` structure, you will add this file in this location: `data/pokemeteors/structure/medium/ignore_solmeteor.nbt`. You can also decide to ignore all default meteors added by the mod and add your own instead. In this case you can place a file nameed `ignore_defualt.nbt` in the `structure` root folder.

To spawn a pokemon, inside the meteor structure you must place a sign
 (of any kind) where you write `pokespawn`. The pokemon will then spawn in that position when the metor lands

<img width="1920" height="991" alt="sign example" src="https://github.com/user-attachments/assets/6bc03446-2e66-44fc-851a-fb491ef5c373" />

### Example datapack
You can find a link to a datapack example [here](https://github.com/Emafire003/Pokemeteors/releases/download/datapack-v0/pokemeteors_example_datapack.zip)

If you have questions, open an issue on GitHub or shoot me a message on discord at @Emafire003

### Config
By installing YetAnotherConfigLib and ModMenu you can tweak a few settings, regarding the spawn of meteors when using the /pokespawn command as well as when and if to announce meteor spawns along with which pokémon they contain etc

### Support
The best way to do that, is sharing the mod with as many people as you know, feel free to make video tutorials/gameplayes and such! It would really help!

You can directly support me by offering a coffe at this link:
[![ko-fi](https://ko-fi.com/img/githubbutton_sm.svg)](https://ko-fi.com/S6S88307C)

### License
GNU GPL3. Yes you can add this mod to modpacks without asking me.
