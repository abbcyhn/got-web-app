import json

def find_unique_properties(data):
    unique_properties = set()
    
    def extract_properties(obj):
        if isinstance(obj, dict):
            for key, value in obj.items():
                unique_properties.add(key)
                extract_properties(value)
        elif isinstance(obj, list):
            for item in obj:
                extract_properties(item)
    
    extract_properties(data)

    unique_properties_list = list(unique_properties)
    unique_properties_list.sort()
    return unique_properties_list

file_path = 'got-characters.json'
with open(file_path, 'r') as file:
    data = json.load(file)

unique_properties = find_unique_properties(data)

for prop in unique_properties:
    print(prop)
