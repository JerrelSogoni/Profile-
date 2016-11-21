import os
filename = "../rsrc/FriendsWith.txt"
def createInsert():
	with open(filename, "r") as f:
		for line in f:
			spl = line.split(':')

			print 'INSERT INTO FriendsWith VALUES(%s, %s);' % (spl[0], spl[1].strip())


createInsert()
