# [Pathom Tutorial](https://pathom3.wsscode.com/docs/tutorial) Code

## Potentially Helpful Prerequisites

* [jvm](https://clojure.org/guides/install_clojure#java) (>= 17 for flowstorm use)
* [clojure cli and related](https://clojure.org/guides/install_clojure)
* some editor
* sufficient network access
* ability and willingness to read and follow instructions
* luck and skill at filling in gaps and correcting for errors (^^;

## Sample Steps for Using with FlowStorm

```
# start jvm (17) with clojure and :dev alias (see `deps.edn`)
$ JAVA_HOME=/usr/lib/jvm/openjdk17 PATH=$JAVA_HOME/bin:$PATH clj -A:dev

# start flowstorm gui (and confirm gui shows up - there is a dark theme fwiw)
user=> :dbg

# load tutorial file (and wait a bit)
user=> (load-file "src/demos/ip_weather.clj")

# switch to tutorial file namespace
user=> (in-ns 'demos.ip-weather)

# turn on recording in flowstorm gui (button with circle icon in flows tab)

# try out
demos.ip-weather=> (-> (p.eql/process env {:ip "198.29.213.3"} [:temperature])
                       :temperature
                       number?)

# turn off recording in flowstorm gui (same button as before, but icon diff)

# explore results in flows tab :)
```

