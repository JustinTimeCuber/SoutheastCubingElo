import urllib.request
import json

inputfile = open("elos.txt", "r")
previous = open("output.csv", "r")
names = {}
for line in previous:
  sp = line.split(",")
  wcaid = sp[1]
  name = sp[2]
  if wcaid != "WCA ID":
    names[wcaid] = name
previous.close()
outputfile = open("output.csv", "w")
rank = 1
outputfile.write(",".join(["Rank", "WCA ID", "Name", "Elo"]) + "\n")
for line in inputfile:
  sp = line.split(": ")
  wcaid = sp[0]
  elo = sp[1]
  name = ""
  if wcaid in names:
    name = names[wcaid]
  else:
    link = "https://raw.githubusercontent.com/robiningelbrecht/wca-rest-api/master/api/persons/" + wcaid + ".json"
    f = urllib.request.urlopen(link)
    rawjson = f.read()
    compjson = json.loads(rawjson)
    name = compjson["name"]
  outputfile.write(",".join([str(rank), wcaid, name, str(elo)]))
  rank += 1
