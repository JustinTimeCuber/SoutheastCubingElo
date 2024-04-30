import urllib.request
import sys
import json

link = "https://raw.githubusercontent.com/robiningelbrecht/wca-rest-api/master/api/results/" + sys.argv[1] + "/333.json"
f = urllib.request.urlopen(link)
rawjson = f.read()
compjson = json.loads(rawjson)
rounds = []
for competitor in compjson["items"]:
  if competitor["position"] == 1:
    rounds += [competitor["personId"]]
  else:
    rounds[-1] += " " + competitor["personId"]
rounds.reverse()
out = open("input.txt", "a")
out.write("\n")
out.write("# " + sys.argv[1] + "\n")
for r in rounds:
  out.write(r + "\n")
