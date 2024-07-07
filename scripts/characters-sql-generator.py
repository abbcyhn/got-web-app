import json

class Expando(object):
    pass

def parse_character(character_id, character_data):
    character = Expando()
    character.id = character_id
    character.name = character_data.get("characterName", "").replace("'", "''")
    character.nickname = character_data.get("nickname", "").replace("'", "''")
    character.houseName = character_data.get("houseName", "")
    character.royal = str(character_data.get("royal", False)).upper()
    character.kingsguard = str(character_data.get("kingsguard", False)).upper()
    character.imageFull = character_data.get("characterImageFull", "")
    character.imageThumb = character_data.get("characterImageThumb", "")
    character.characterLink = character_data.get("characterLink", "")

    return character
    
def parse_relationship(relationship_id, character_id, relationTo, relationType):
    relationship = Expando()
    relationship.id = relationship_id
    relationship.characterId = character_id
    relationship.relationTo = relationTo
    relationship.relationType=relationType
    return relationship

def parse_action(action_id, character_id, actionTo, actionType):
    action = Expando()
    action.id = action_id
    action.characterId = character_id
    action.actionTo = actionTo
    action.actionType = actionType
    return action

def parse_ally(ally_id, character_id, al):
    ally = Expando()
    ally.id = ally_id
    ally.characterId = character_id
    ally.allyId = al
    return ally

def parse_actor(actor_id, character_id, actor_name, actor_link):
    actor = Expando()
    actor.id = actor_id
    actor.characterId = character_id
    actor.actorName = actor_name
    actor.actorLink = actor_link
    return actor

def parse_house(house_id, character_id, house_name):
    house = Expando()
    house.id = house_id
    house.characterId = character_id
    house.houseName = house_name.upper()
    return house

def parse_season(season_id, actor_id, seasonNumber):
    season = Expando()
    season.id = season_id
    season.actorId = actor_id
    season.seasonNumber = seasonNumber
    return season

def parse_data_helper(data):
    name_to_id = {}

    characters = []
    actors= []
    seasons = []
    houses = []

    # dirty means names with not corresponding ids
    relationships = [] 
    actions = []
    allies = []

    character_id = 1
    relationship_id = 1
    action_id = 1
    ally_id = 1
    actor_id = 1
    season_id = 1
    house_id = 1
    for character_data in data["characters"]:
        # characters
        character = parse_character(character_id, character_data)
        characters.append(character)
        name_to_id[character.name] = character_id

        # dirty relationships - parentOf
        for rel in character_data.get("parentOf", []):
            relationship = parse_relationship(relationship_id, character_id, rel, 'PARENT')
            relationships.append(relationship)
            relationship_id += 1

        # dirty relationships - parents
        for rel in character_data.get("parents", []):
            relationship = parse_relationship(relationship_id, rel, character_id, 'PARENT')
            relationships.append(relationship)
            relationship_id += 1

        # dirty relationships - siblings
        for rel in character_data.get("siblings", []):
            relationship = parse_relationship(relationship_id, character_id, rel, 'SIBLING')
            relationships.append(relationship)
            relationship_id += 1

        # dirty relationships - marriedEngaged
        for rel in character_data.get("marriedEngaged", []):
            relationship = parse_relationship(relationship_id, character_id, rel, 'MARRIED_ENGAGED')
            relationships.append(relationship)
            relationship_id += 1

        # dirty actions - 'abducted'
        for act in character_data.get("abducted", []):
            action = parse_action(action_id, character_id, act, 'ABDUCTED')
            actions.append(action)
            action_id += 1

        # dirty actions - 'abductedBy'
        for act in character_data.get("abductedBy", []):
            action = parse_action(action_id, act, character_id, 'ABDUCTED')
            actions.append(action)
            action_id += 1

        # dirty actions - 'killed'
        for act in character_data.get("killed", []):
            action = parse_action(action_id, character_id, act, 'KILLED')
            actions.append(action)
            action_id += 1

        # dirty actions - 'killedBy'
        for act in character_data.get("killedBy", []):
            action = parse_action(action_id, act, character_id, 'KILLED')
            actions.append(action)
            action_id += 1

        # dirty actions - 'serves'
        for act in character_data.get("serves", []):
            action = parse_action(action_id, character_id, act, 'SERVED')
            actions.append(action)
            action_id += 1

        # dirty actions - 'servedBy'
        for act in character_data.get("servedBy", []):
            action = parse_action(action_id, act, character_id, 'SERVED')
            actions.append(action)
            action_id += 1

        # dirty actions - 'guardianOf'
        for act in character_data.get("guardianOf", []):
            action = parse_action(action_id, character_id, act, 'GUARDED')
            actions.append(action)
            action_id += 1    

        # dirty actions - 'guardedBy'
        for act in character_data.get("guardedBy", []):
            action = parse_action(action_id, act, character_id, 'GUARDED')
            actions.append(action)
            action_id += 1 

        # dirty allies
        for al in character_data.get("allies", []):
            ally = parse_ally(ally_id, character_id, al)
            allies.append(ally)
            ally_id += 1

        # actors
        actor_name = character_data.get("actorName", "").replace("'", "''")
        actor_link = character_data.get("actorLink", "")
        if actor_name != "":
            actor = parse_actor(actor_id, character_id, actor_name, actor_link)
            actors.append(actor)
            actor_id += 1

        for actor_data in character_data.get("actors", []):
            actor_name = actor_data.get("actorName", "").replace("'", "''")
            actor_link = actor_data.get("actorLink", "")
            actor = parse_actor(actor_id, character_id, actor_name, actor_link)
            actors.append(actor)
            actor_id += 1
            for seasonNumber in actor_data.get("seasonsActive", []):
                season = parse_season(season_id, actor_id, seasonNumber)
                seasons.append(season)
                season_id += 1


        if isinstance(character_data.get("houseName"), str):
            house_name = character_data.get("houseName", "").replace("'", "''")
            house = parse_house(house_id, character_id, house_name)
            houses.append(house)
            house_id += 1
        else:
            for house_name in character_data.get("houseName", []):
                house = parse_house(house_id, character_id, house_name)
                houses.append(house)
                house_id += 1

        character_id += 1

    return characters, actors, seasons, houses, relationships, actions, allies, name_to_id 

def fix_relationships(relationships, name_to_id):
    for relationship in relationships:
        characterId = relationship.characterId
        if type(characterId) == str and not characterId.isnumeric():
            if name_to_id.get(characterId, "") == "":
                relationship.id = None
            else:
                relationship.characterId = name_to_id.get(characterId)

        relationTo = relationship.relationTo
        if type(relationTo) == str and not relationTo.isnumeric():
            if name_to_id.get(relationTo, "") == "":
                relationship.id = None
            else:
                relationship.relationTo = name_to_id.get(relationTo)

def fix_actions(actions, name_to_id):
    for action in actions:
        characterId = action.characterId
        if type(characterId) == str and not characterId.isnumeric():
            if name_to_id.get(characterId, "") == "":
                action.id = None
            else:
                action.characterId = name_to_id.get(characterId)

        actionTo = action.actionTo
        if type(actionTo) == str and not actionTo.isnumeric():
            if name_to_id.get(actionTo, "") == "":
                action.id = None
            else:
                action.actionTo = name_to_id.get(actionTo)

def fix_allies(allies, name_to_id):
    for ally in allies:
        allyId = ally.allyId
        if type(allyId) == str and not allyId.isnumeric():
            if name_to_id.get(allyId, "") == "":
                ally.id = None
            else:
                ally.allyId = name_to_id.get(allyId)

def parse_data(data):
    characters, actors, seasons, houses, relationships, actions, allies, name_to_id = parse_data_helper(data)

    # replace names with ids
    fix_relationships(relationships, name_to_id)
    fix_actions(actions, name_to_id)
    fix_allies(allies, name_to_id)

    return characters, relationships, actions, allies, actors, seasons, houses




def generate_insert_statements(data):
    characters_inserts = []
    relationships_inserts = []
    actions_inserts = []
    allies_inserts = []
    seasons_inserts = []
    actors_inserts = []
    houses_inserts = []

    characters, relationships, actions, allies, actors, seasons, houses = parse_data(data)

    # sql insert templates
    character_template = ("INSERT INTO Characters (id, name, nickname, link, royal, kingsguard, image_full, image_thumb) "
                          "VALUES ({id}, '{name}', '{nickname}', '{characterLink}', {royal}, {kingsguard}, '{imageFull}', '{imageThumb}');")
    relationship_template = ("INSERT INTO Relationships (id, character_id, relation_to, relation_type) "
                             "VALUES ({id}, {characterId}, {relationTo}, '{relationType}');")
    action_template = ("INSERT INTO Actions (id, character_id, action_to, action_type) "
                       "VALUES ({id}, {characterId}, {actionTo}, '{actionType}');")
    ally_template = ("INSERT INTO Allies (id, character_id, ally_id) "
                       "VALUES ({id}, {characterId}, {allyId});")
    actor_template = ("INSERT INTO Actors (id, character_id, name, link) "
                       "VALUES ({id}, {characterId}, '{actorName}', '{actorLink}');")
    season_template = ("INSERT INTO Active_Seasons (id, actor_id, season_number) "
                       "VALUES ({id}, {actorId}, {seasonNumber});")
    house_template = ("INSERT INTO Houses (id, character_id, name) "
                       "VALUES ({id}, {characterId}, '{houseName}');")

    for character in characters:
        characters_inserts.append(character_template.format(
            id=character.id,
            name=character.name,
            nickname=character.nickname,
            houseName=character.houseName,
            royal=character.royal,
            kingsguard=character.kingsguard,
            imageFull=character.imageFull,
            imageThumb=character.imageThumb,
            characterLink=character.characterLink
        ))

    added_rels = set()
    for rel in relationships:
        added_rel1 = str(rel.characterId) + "" + str(rel.relationTo) + "" + str(rel.relationType)
        added_rel2 = str(rel.relationTo) + "" + str(rel.characterId) + "" + str(rel.relationType)
        if rel.id is None or added_rel1 in added_rels or added_rel2 in added_rels: continue
        added_rels.add(added_rel1)
        added_rels.add(added_rel2)
        relationships_inserts.append(relationship_template.format(
            id=rel.id,
            characterId=rel.characterId,
            relationTo=rel.relationTo,
            relationType=rel.relationType
        ))

    added_acts = set()
    for act in actions:
        added_act1 = str(act.characterId) + "" + str(act.actionTo) + "" + str(act.actionType)
        added_act2 = str(act.actionTo) + "" + str(act.characterId) + "" + str(act.actionType)
        if act.id is None or added_act1 in added_acts or added_act2 in added_acts: continue
        added_acts.add(added_act1)
        added_acts.add(added_act2)
        actions_inserts.append(action_template.format(
            id=act.id,
            characterId=act.characterId,
            actionTo=act.actionTo,
            actionType=act.actionType
        ))

    for ally in allies:
        allies_inserts.append(ally_template.format(
            id=ally.id,
            characterId=ally.characterId,
            allyId=ally.allyId,
        ))

    for actor in actors:
        actors_inserts.append(actor_template.format(
            id=actor.id,
            characterId=actor.characterId,
            actorName=actor.actorName,
            actorLink=actor.actorLink,
        ))

    for season in seasons:
        seasons_inserts.append(season_template.format(
            id=season.id,
            actorId=season.actorId,
            seasonNumber=season.seasonNumber,
        ))

    for house in houses:
        houses_inserts.append(house_template.format(
            id=house.id,
            characterId=house.characterId,
            houseName=house.houseName,
        ))

    return characters_inserts, relationships_inserts, actions_inserts, allies_inserts, actors_inserts, seasons_inserts, houses_inserts

got_characters_json = 'got-characters.json'
characters_inserts_sql = 'sqls/2.got-inserts-characters.sql'
actors_inserts_sql = 'sqls/3.got-inserts-actors.sql'
actions_inserts_sql = 'sqls/4.got-inserts-actions.sql'
allies_inserts_sql = 'sqls/5.got-inserts-allies.sql'
houses_inserts_sql = 'sqls/6.got-inserts-houses.sql'
relationships_inserts_sql = 'sqls/7.got-inserts-relationships.sql'
seasons_inserts_sql = 'sqls/8.got-inserts-seasons.sql'

with open(got_characters_json) as f:
    data = json.load(f)

characters_inserts, relationships_inserts, actions_inserts, allies_inserts, actors_inserts, seasons_inserts, houses_inserts = generate_insert_statements(data)



# clear
open(characters_inserts_sql, 'w').close()
open(relationships_inserts_sql, 'w').close()
open(actions_inserts_sql, 'w').close()
open(allies_inserts_sql, 'w').close()
open(actors_inserts_sql, 'w').close()
open(seasons_inserts_sql, 'w').close()
open(houses_inserts_sql, 'w').close()

# write
with open(characters_inserts_sql, 'a') as f:
    f.write("\n".join(characters_inserts) + "\n")

with open(relationships_inserts_sql, 'a') as f:
    f.write("\n".join(relationships_inserts) + "\n")

with open(actions_inserts_sql, 'a') as f:
    f.write("\n".join(actions_inserts) + "\n")

with open(allies_inserts_sql, 'a') as f:
    f.write("\n".join(allies_inserts) + "\n")

with open(actors_inserts_sql, 'a') as f:
    f.write("\n".join(actors_inserts) + "\n")

with open(seasons_inserts_sql, 'a') as f:
    f.write("\n".join(seasons_inserts) + "\n")

with open(houses_inserts_sql, 'a') as f:
    f.write("\n".join(houses_inserts) + "\n")