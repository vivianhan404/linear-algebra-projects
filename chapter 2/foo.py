import re
import numpy as np
  
def scan_in(question):
  file_name = "q" + question + ".txt"
  q = open(file_name, "r")
  
  # convert data to 2d list of ints
  line = ""
  data = []
  raw = q.readlines()
  for cur_line in raw:
    line += re.sub(r'[\s"]', "", cur_line)
    if line[-2] == "}":
      line = line[1:-2].split(",")
      line = [int(x) for x in line]
      data.append(line)
      line = ""
  q.close()

  names = line.split(",")
  data = np.array(data)
  print(data)
  return data, names

def power(data):
  print("victories: ")
  print(data.sum(axis = 1))
  print("\nA + A^2")
  sum = data + (data @ data)
  print(sum)
  print("\npower: ")
  pow = sum.sum(axis = 1)
  print(pow)
  return pow

def sort(pow, names):
  ''' 
  Given the powers and names of teams, sort the teams in power-order 
  '''
  s = [ (pow[i], names[i]) for i in range(pow.length)]
  print(s)
  s.sort
  print(s)
  for t in s:
    print(t[1])