import os
filename = "../rsrc/HasAccessToGroup.txt"
def createInsert():
	with open(filename, "r") as f:
		for line in f:
			spl = line.split(':')

			print 'INSERT INTO HasAccessToGroup VALUES(%s, %s, %s);' % (spl[0], spl[1], spl[2].strip())


createInsert()
