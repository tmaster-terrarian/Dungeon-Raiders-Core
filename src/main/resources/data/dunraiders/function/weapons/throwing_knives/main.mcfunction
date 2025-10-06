execute as @e[type=kitchenprojectiles:knife] at @s \
    unless function dunraiders:util/check_in_ground \
    if block ~ ~ ~ #dunraiders:throwing_knife_destroyable \
    unless block ~ ~ ~ #dunraiders:throwing_knife_immune \
    run function dunraiders:weapons/throwing_knives/destroy_blocks
