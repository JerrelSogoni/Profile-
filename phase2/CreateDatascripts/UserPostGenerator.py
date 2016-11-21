import os
filename = "../rsrc/MockPost.txt"
def createInsert():
	with open(filename, "r") as f:
		for line in f:
			spl = line.split(':')

			print 'INSERT INTO Post VALUES(%s, %s, \'%s\', %s , %s);' % (spl[0], spl[1], spl[2], spl[3], spl[4].strip())


createInsert()
