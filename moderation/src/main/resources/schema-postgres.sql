DROP TYPE IF EXISTS ticketstatus CASCADE;
CREATE TYPE ticketstatus AS ENUM ('OPEN', 'PICKED', 'RESOLVED', 'ABUSIVE', 'INVALID', 'DELETED');