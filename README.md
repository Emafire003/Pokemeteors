# Pokémeteors mod (maybe use the blockbench title thing)
A mod that allows space-adjacent (or normal!) cobblemon pokémons to spawn inside a falling meteorite, 
courtesy of the OhMyMeteors mod!

//gif goes here//

When a pokémon normally spawns, the mod checks if it should spawn inside a meteor (configurable with datapacks), and with which chance. Then, a falling meteor
spanws in the sky above the pokémon's spawn position, creating an impact crater along with a meteor and the pokémon itself. The pokémon follows all of the normal
spawn rules you can define with Cobblemon's datapacks, so for example if it should have spawned only in cold biomes, it will spawn only in cold biomes, if it was supposed
to be of a level between 20 and 30, it will have a level between 20 and 30 and so on. 

//sponsor goes here
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
      "chance": 1 in x chances,
      "max_meteor_size": 10,
      "min_meteor_size": 5,
      "meteor_size_class": "small|medium|big|huge",
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
- `species`: The id of the pokemon that should spawn with a meteor
- `chance`: The chance of this pokemon spawing with a meteor vs spawning normally, expressed as 1 in every <x> chance of this happening. (For example, setting it to `2` will make the pokemon spawn with a 50% chance, aka half of the times)
- `max_meteor_size`: A number between 1 and 50, represents how big the meteor can be at most. The bigger the meteor, the bigger the explosion. And the meteor entity that gets rendered in game
- `min_meteor_size`: Same as above, but the minimum size
- `meteor_size_class`: One of 4 possible values, `small`, `medium`, `big`, `huge`. This value is used to determine how deep the meteor structure will be embedded, and a rough description of how big the meteor is going to be. It also used to determine with which meteor structure the pokemon will spawn, in fact the to each class corresponds a set of meteor structure types
- `unique_meteor`: An option value, overrides the meteor structure selected by the meteor size class and instead spawns a specfic structure, given its id. 
- `aspect_unique_meteor`: similar to the option above, it's a map of `aspect:structure_id`, and it will spawn a unique meteor structure for the given aspect. An example is Minior, with each core color spawning with its own meteor strucure

`override_for` lets you specify overrides on specific pokemons, for example let's say you want to spawn a minior with your own structure, but the default datapack spawns with the default colored meteors. By putting `"cobblemon:minior"` in the list your config will overidde the default (or other) values. To override all of the default pokemon spawns you can add as a first element of the list `"all_default"`

### Config
By installing YetAnotherConfigLib and ModMenu you can tweak a few settings, regarding the spawn of meteors when using the /pokespawn command as well as when and if to announce meteor spawns along with which pokémon they contain etc

### Support
Links and stuff TODO

### License
GNU GPL3. Yes you can add this mod to modpacks without asking me.