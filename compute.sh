#!/bin/bash
rm input.txt
touch input.txt
echo "Downloading competition data..."
echo "START" >> input.txt
echo "" >> input.txt
while read p; do
  python3 comp-json-format.py $p >> input.txt
done < comps.txt
echo "Calculating elos..."
java SimpleEloCalculator
echo "Downloading name data and creating spreadsheet..."
python3 create-csv.py
