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
print("")
print("# " + sys.argv[1])
for r in rounds:
  print(r)
