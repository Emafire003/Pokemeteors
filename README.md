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
### Suggested config for OhMyMeteors
Here are some suggestions on how to edit Oh My, Meteors! config to have a better experience when using it with this mod:
- `meteor_spawn_chance:-1` disables natural meteor spawn
- area_explosion_sound:true allows
- area_explosion_sound_radius:200


<details>
  <summary>Config file version 7 version</summary>
  
  ```yaml
  #The version of the config. DO NOT CHANGE IT :D | default= 7 | type= Integer
version:7

#Expressed in blocks, represents the min distance (as in a radius) from the origin of the meteor (like a player) in which the meteor wont' spawn in. (Remember that it has an angled trajectory so it could end up in that area regardless) | default= 2 | type= Integer
min_meteor_spawn_distance:10
#Expressed in blocks, represents the max distance (as in a radius) from the origin of the meteor (like a player) in which a meteor can spawn in. (Remember that it has an angled trajectory so it could end up in that area regardless) | default= 25 | type= Integer
max_meteor_spawn_distance:50
#The world height (y level) at which meteors spawn in | default= 300 | type= Integer
meteor_spawn_height:300
#The higher the value the less the meteor will go diagonally, and will keep mostly vertical. (randomness remains so it's not 100%). Generally, you can remain between 1 and 10. | default= 3.1 | type= Double
meteor_dispersion_factor:3.1
#Expressed as '1 in <x>' chances of spawning a meteor each tick (similar to randomTickSpeed). Setting it to a negative value will disable natural meteor spawn For example, by default it has a chance of 1 in 20000 | default= 30000 | type= Integer
meteor_spawn_chance:-1
#Should huge meteors be able to spawn? They are meteors bigger than the maximum size of the big ones | default= true | type= Boolean
spawn_huge_meteors:true
#The chance for a spawned meteor to be of huge size. Expressed as in 1 in x chances. (on top of the 'normal' spawning chance) | default= 100 | type= Integer
huge_meteor_chance:100
#The size limit of how big a huge meteor can be | default= 40 | type= Integer
huge_meteor_size_limit:40
#Should the spawn rate be different during the night? | default= false | type= Boolean
modify_spawn_chance_at_night:false
#The chance for a meteor to spawn at night if enabled. Expressed as in 1 in x chances. | default= 10000 | type= Integer
meteor_night_spawn_chance:10000
#The smallest size a natural meteor can have when spawned in. Cannot go below 1 | default= 1 | type= Integer
natural_meteor_min_size:1
#The biggest size a natural meteor can have when spawned in. Cannot go above 50. | default= 10 | type= Integer
natural_meteor_max_size:10
#A factor to ADD to the explosion power (by default, the power is equal to the meteor size), thus increasing the damage and radius of the explosion. Also supports negative numbers | default= 0 | type= Integer
explosion_power_modifier:0
#A factor to ADD to the speed at which the meteor falls downwards. It is added to a randomly generated number between 1 and 0. Also supports negative numbers | default= 0 | type= Integer
downwards_speed_modifier:0
#Should meteors explode when they come into contact with an entity? (if true you could use an arrow to make the meteor explode for example) | default= false | type= Boolean
explode_on_entity_collision:false

#This option has no effect as of version 0.4.0. Please use the 'ohmymeteors:meteor_bypasses' tag instead. | default= true | type= Boolean
should_bypass_leaves:true
#This option has no effect as of version 0.4.0. Please use the 'ohmymeteors:meteor_bypasses_and_destroy' tag instead | default= true | type= Boolean
should_destroy_leaves:true
#Should meteors be (more or less) directed towards the nearest player? | default= false | type= Boolean
homing_meteors:false
#Should players get a message in chat/hotbar when a meteor spawns? | default= false | type= Boolean
announce_meteor_spawn:false
#Should players get a message in chat/hotbar when a meteor is destroyed? | default= false | type= Boolean
announce_meteor_destroyed:false
#Should the above announcement be displayed above the hotbar in the actionbar or in chat? | default= true | type= Boolean
actionbar_announcements:true
#If announcements are enabled, should they also display the (approximate) coordinates of the meteor being spawned/destroyed? | default= true | type= Boolean
announce_location:true
#Should the explosion sound of the meteor be heard by all players online? | default= false | type= Boolean
global_explosion_sound:false
#Should the explosion sound of the meteor be heard by all players around a certain area from the impact point? | default= false | type= Boolean
area_explosion_sound:true
#The radius in blocks of the area in which the sound of the meteor will be heard if the option above is true | default= 500 | type= Integer
area_explosion_sound_radius:200

#Should there be a cooldown between a meteor spawning one meteor and then another? | default= true | type= Boolean
should_cooldown_between_meteors:true
#The minimum time interval (in seconds) between spawning a meteor and then another | default= 20 | type= Integer
min_meteor_cooldown_time:20
#Should meteors be able to destroy blocks on impact? | default= true | type= Boolean
meteor_griefing:true
#Should the meteors that come out of a bigger meteor when it's broken be able to destroy blocks on impact? | default= true | type= Boolean
scatter_meteor_griefing:false
#Should meteors spawn the meteor structure after impact? | default= true | type= Boolean
meteor_structure:true
#Should the meteors that come out of a bigger meteor when it's broken be able to destroy spawn structures on impact? | default= true | type= Boolean
scatter_meteor_structure:false
#Should the meteor structure only replace air blocks? | default= false | type= Boolean
only_replace_air:false
#Should the meteors that come out of a bigger meteor when it's broken only replace air blocks for their structure? | default= true | type= Boolean
scatter_only_replace_air:true
#Should fire be spawned on meteor impact? (it looks cool!) | default= true | type= Boolean
spawn_fire_with_meteor:false

#The radius in blocks of the xz area covered by the Basic laser block, where meteors will be blown up | default= 32 | type= Integer
basic_laser_area_radius:32
#How many blocks up from the position of the basic laser should meteors be checked for? (note that the detection box is only 2 blocks thick, not the whole way) | default= 64 | type= Integer
basic_laser_height:64
#The radius in blocks of the xz area covered by the advanced laser block, where meteors will be blown up | default= 48 | type= Integer
advanced_laser_area_radius:48
#How many blocks up from the position of the advanced laser should meteors be checked for? (note that the detection box is only 2 blocks thick, not the whole way) | default= 64 | type= Integer
advanced_laser_height:64
#Should the laser be in a cooldown where it can't fire, after it has just fired? | default= true | type= Boolean
should_basic_laser_cooldown:true
#How many seconds should this cooldown last? | default= 3 | type= Integer
basic_laser_cooldown:3
#Should the laser be in a cooldown where it can't fire, after it has just fired? | default= true | type= Boolean
should_advanced_laser_cooldown:true
#How many seconds should this cooldown last? | default= 1 | type= Integer
advanced_laser_cooldown:1

#The maximum size of meteor that can be considered small, and will spawn a small meteor structure upon impact | default= 4 | type= Integer
max_small_meteor_size:4
#The maximum size of meteor that can be considered medium, and will spawn a medium meteor structure upon impact | default= 7 | type= Integer
max_medium_meteor_size:7
#The maximum size of meteor that can be considered big, and will spawn a big meteor structure upon impact. Only these can spawn a meteor cat by default. | default= 20 | type= Integer
max_big_meteor_size:20
#The chance of spawning a special meteor structure in a certain size category (for example the meteor cat meteor) (works like the other chances, aka 1 in x probability) | default= 10 | type= Integer
special_meteors_chance:10

#Should meteor and laser particles be forced? They will be rendered further away and look better, but if there are too many of them you may want to disable this for lag reasons. Note: some particles will never displays as forced, like the lasers target box | default= true | type= Boolean
use_forced_particles:true

#A list of the IDs of the dimensions in which meteors can naturally spawn in, vanilla or not. | default= [minecraft:overworld, minecraft:the_end] | type= List12
spawn_dimensions:[minecraft:overworld, minecraft:the_end]
#A map consisting of dimension=chance of spawning. The dimension must be present in the list above, otherwise meteors won't spawn at all. This chance will ALWAYS ovveride the default if present. The spawn chance works as described above. | default= {minecraft:the_end=300000} | type= Map1
dimension_chances:{minecraft:the_end=300000}
#The same as above but with a possibly different chance at night if enabled | default= {minecraft:the_end=100000} | type= Map1
dimension_night_chances:{minecraft:the_end=100000}
#If set to false will behave like a blacklist, aka meteors won't spawn in those biomes. If true will behave like a whitelist, meteors will spawn ONLY in those biomes. | default= false | type= Boolean
biome_list_mode:false
#A black or whitelist of the biomes in which meteor will or will not spawn according to the setting above | default= [minecraft:cherry_grove, minecraft:soul_sand_valley, modname:modbiome] | type= ListN
biome_spawn_list:[modname:modbiome]
#A map consisting of biome=chance of spawning. The spawn chance works as described above. The meteors must be able to spawn in those biomes, otherwise they won't! | default= {minecraft:desert=29990, modname:modbiome=2025} | type= MapN
biome_chances:{modname:modbiome=2025}
#The same as above but with a possibly different chance at night if enabled | default= {minecraft:desert=9995, modname:modbiome=2025} | type= MapN
biome_night_chances:{modname:modbiome=2025}

#If true will use a spherical explosion instead of the vanilla cubical one. These look nicer at higher explosion power/ranges, but after power 100 become quite laggy. | default= true | type= Boolean
use_better_explosions:true
#How far should meteors be rendered. WARNING: YOU NEED TO RESTART YOUR SERVER AND CLIENT IF YOU CHANGE THIS VALUE in order for it to take effect. It is multiplied by the 'entity render' distance of the server | default= 200 | type= Integer
meteor_render_distance:200

#If true, there will be a chance that meteor showers will spawn (a lot of meteors spawning at the same time & place. it can also cause brief lag spikes, nothing too dramatic tho) | default= true | type= Boolean
meteor_showers_enabled:true
#The chance for a meteor shower to spawn (on top of the normal meteor spawning chance, so it's meteor_spawn*meteor_shower spawn) | default= 100 | type= Integer
meteor_shower_chance:100
#The minimum number of meteors that are going to spawn in the meteor shower. | default= 5 | type= Integer
min_meteors_in_shower:5
#The maximum number of meteors that are going to spawn in the meteor shower. | default= 20 | type= Integer
max_meteors_in_shower:20
#The delay (in ticks) between each meteor that gets spawned in delayed and direction delayed meteor showers | default= 15 | type= Integer
meteor_shower_delay_ticks:15
#If true, makes the sky glow a certain color when a meteor passes by. This only applies if the meteor is in rendering range and not world or server wide. | default= true | type= Boolean
meteor_skyglow:true
#The color to apply to the sky when a meteor passes by if it's enabled. By default it's a lightblue-cyan color. Bear in mind that Minecraft still applies its own colors, so some shades (like green) work less well than others (blue) | default= #048da5 | type= String
meteor_skyglow_color:#048da5

  ```
  
</details>

### Support
The best way to do that, is sharing the mod with as many people as you know, feel free to make video tutorials/gameplayes and such! It would really help!

You can directly support me by offering a coffe at this link:
[![ko-fi](https://ko-fi.com/img/githubbutton_sm.svg)](https://ko-fi.com/S6S88307C)

### License
GNU GPL3. Yes you can add this mod to modpacks without asking me.
