import os
filename = "../rsrc/MockUserComment.txt"
def createInsert():
	with open(filename, "r") as f:
		for line in f:
			spl = line.split(':')

			print 'INSERT INTO Comment VALUES(%s, %s, \'%s\', %s);' % (spl[0], spl[1], spl[2], spl[3].strip())


createInsert()
