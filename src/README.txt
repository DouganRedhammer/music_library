2/8/13
    Currently uses Hibernate to model the database with at least one of each of the following.
    one-to-one
    one-to-many
    many-to-many

    Uses Criteria API and some HQL to do queries.

    You must first create the database music_library and set the appropriate permissions.
    If the DB is empty run the following commands in order.
        create
        load

    If the tables and data already exists type:
        albums
    That command will display all the albums alphabetically with artist, track and composer info.