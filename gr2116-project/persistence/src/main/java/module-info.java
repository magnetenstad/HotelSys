module gr2116.persistence {
  requires transitive org.json;
  requires transitive gr2116.core;
  requires transitive com.fasterxml.jackson.databind;
  requires transitive org.apache.commons.io;

  exports gr2116.persistence;
}