#!/usr/bin/env bash
echo "Creating client..."
set -e;

mongo -- "$MONGO_INITDB_DATABASE" <<-EOF
    db.auth_client_details.insert({
        _class: "com.kajti.auth.domain.AuthClientDetails",
        _id: UUID(),
        clientId: "$1",
        clientSecret: "$2",
        grantTypes: "$3",
        scopes: "$4"
    });
EOF