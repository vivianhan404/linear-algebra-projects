from foo import *
question = "6"
data, names = scan_in(question)
print()
print(names)
print(data)
p = power(data)

# TODO: create sort function, answer 5 and 6

if (question == "5"):
  sort(p, names)

print('exit code 0')

