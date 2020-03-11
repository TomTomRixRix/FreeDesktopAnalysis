import json

with open('desktops.txt') as f:
  data = json.load(f)

f = open('desktops.csv', 'w')
for loc in data['data']:
    f.write("{},{},{},{},{},{},{},{}\n".format(loc['location']['roomname'], loc['location']['latitude'], loc['location']['longitude'], loc['location']['building_name'], loc['location']['address'].replace(","," "), loc['location']['postcode'], loc['free_seats'], loc['total_seats']))
    
f.close()

