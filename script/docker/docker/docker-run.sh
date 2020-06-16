echo "docker-run.sh BEGIN"
set -e

BROKER_HOME=/var/lib/
CONFIG_PATH=$BROKER_HOME/etc
export BROKER_HOME OVERRIDE_PATH CONFIG_PATH

if [[ ${ANONYMOUS_LOGIN,,} == "true" ]]; then
  LOGIN_OPTION="--allow-anonymous"
else
  LOGIN_OPTION="--require-login"
fi

CREATE_ARGUMENTS="--user ${ARTEMIS_USER} --password ${ARTEMIS_PASSWORD} --silent ${LOGIN_OPTION} ${EXTRA_ARGS}"

echo CREATE_ARGUMENTS=${CREATE_ARGUMENTS}

if ! [ -f ./etc/broker.xml ]; then
    /opt/activemq-artemis/bin/artemis create ${CREATE_ARGUMENTS} .
else
    echo "broker already created, ignoring creation"
fi

echo "docker-run.sh END - run artemis"
echo "$@"
exec ./bin/artemis "$@"