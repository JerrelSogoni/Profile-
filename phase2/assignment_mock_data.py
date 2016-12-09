with open('user_data.in') as f:
    content = f.readlines()
    for line in content:
        split = line.split()
        print "INSERT INTO UserPlus (UserId, FirstName, LastName, Sex, Email, Password, DOB, Address, City, State, ZipCode, Phone, Preferences, AccountNum) VALUES(%s, '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', %s, '%s', '%s', %s);" \
        % \
        (split[0], split[1], split[2], split[3], split[4], split[5], split[6].split('/')[2] + '-' + split[6].split('/')[0] + '-' + split[6].split('/')[1], 
                "Somewhere",  split[7], split[8], split[9], split[10], split[11], str(int(split[0]) + 800000))
