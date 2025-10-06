execute as @e[type=#minecraft:arrows] at @s \
    unless function dunraiders:util/check_in_ground \
    run particle minecraft:end_rod ~ ~ ~ 0.1 0.1 0.1 0.02 3 force
