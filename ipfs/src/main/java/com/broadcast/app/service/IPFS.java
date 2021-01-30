//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.broadcast.app.service;

import io.ipfs.api.*;
import io.ipfs.api.NamedStreamable.ByteArrayWrapper;
import io.ipfs.cid.Cid;
import io.ipfs.multiaddr.MultiAddress;
import io.ipfs.multihash.Multihash;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IPFS {
    public static final Version MIN_VERSION = Version.parse("0.4.11");
    public List<String> ObjectTemplates;
    public List<String> ObjectPatchTypes;
    public final String host;
    public final int port;
    public final String protocol;
    private final String version;
    public final Key key;
    public final Pin pin;
    public final Repo repo;
    public final IPFSObject object;
    public final Swarm swarm;
    public final Bootstrap bootstrap;
    public final Block block;
    public final Dag dag;
    public final Diag diag;
    public final Config config;
    public final Refs refs;
    public final Update update;
    public final DHT dht;
    public final File file;
    public final Stats stats;
    public final Name name;
    public final Pubsub pubsub;

    public IPFS(String host, int port) {
        this(host, port, "/api/v0/", false);
    }

    public IPFS(String multiaddr) {
        this(new MultiAddress(multiaddr));
    }

    public IPFS(MultiAddress addr) {
        this(addr.getHost(), addr.getTCPPort(), "/api/v0/", detectSSL(addr));
    }

    public IPFS(String host, int port, String version, boolean ssl) {
        this.ObjectTemplates = Arrays.asList("unixfs-dir");
        this.ObjectPatchTypes = Arrays.asList("add-link", "rm-link", "set-data", "append-data");
        this.key = new Key();
        this.pin = new Pin();
        this.repo = new Repo();
        this.object = new IPFSObject();
        this.swarm = new Swarm();
        this.bootstrap = new Bootstrap();
        this.block = new Block();
        this.dag = new Dag();
        this.diag = new Diag();
        this.config = new Config();
        this.refs = new Refs();
        this.update = new Update();
        this.dht = new DHT();
        this.file = new File();
        this.stats = new Stats();
        this.name = new Name();
        this.pubsub = new Pubsub();
        this.host = host;
        this.port = port;
        if (ssl) {
            this.protocol = "https";
        } else {
            this.protocol = "http";
        }

        this.version = version;

        try {
            Version detected = Version.parse(this.version());
            if (detected.isBefore(MIN_VERSION)) {
                throw new IllegalStateException("You need to use a more recent version of IPFS! >= " + MIN_VERSION);
            }
        } catch (IOException var6) {
            throw new RuntimeException(var6);
        }
    }

    public List<MerkleNode> add(NamedStreamable file) throws IOException {
        return this.add(file, false);
    }

    public List<MerkleNode> add(NamedStreamable file, boolean wrap) throws IOException {
        return this.add(file, wrap, false);
    }

    public List<MerkleNode> add(NamedStreamable file, boolean wrap, boolean hashOnly) throws IOException {
        return this.add(Collections.singletonList(file), wrap, hashOnly);
    }

    public List<MerkleNode> add(List<NamedStreamable> files, boolean wrap, boolean hashOnly) throws IOException {
        Multipart m = new Multipart(this.protocol + "://" + this.host + ":" + this.port + this.version + "add?stream-channels=true&w=" + wrap + "&n=" + hashOnly, "UTF-8");
        Iterator var5 = files.iterator();

        while(var5.hasNext()) {
            NamedStreamable file = (NamedStreamable)var5.next();
            if (file.isDirectory()) {
                m.addSubtree(Paths.get(""), file);
            } else {
                m.addFilePart("file", Paths.get(""), file);
            }
        }

        String res = m.finish();
        return (List) JSONParser.parseStream(res).stream().map((x) -> {
            return MerkleNode.fromJSON((Map)x);
        }).collect(Collectors.toList());
    }

    public List<MerkleNode> ls(Multihash hash) throws IOException {
        Map reply = this.retrieveMap("ls?arg=" + hash);
        return (List)((List)reply.get("Objects")).stream().flatMap((x) -> {
            return ((List)((Map)x).get("Links")).stream().map(MerkleNode::fromJSON);
        }).collect(Collectors.toList());
    }

    public byte[] cat(Multihash hash) throws IOException {
        return this.retrieve("cat?arg=" + hash);
    }

    public byte[] cat(Multihash hash, String subPath) throws IOException {
        return this.retrieve("cat?arg=" + hash + URLEncoder.encode(subPath, "UTF-8"));
    }

    public byte[] get(Multihash hash) throws IOException {
        return this.retrieve("get?arg=" + hash);
    }

    public InputStream catStream(Multihash hash) throws IOException {
        return this.retrieveStream("cat?arg=" + hash);
    }

    public List<Multihash> refs(Multihash hash, boolean recursive) throws IOException {
        String jsonStream = new String(this.retrieve("refs?arg=" + hash + "&r=" + recursive));
        return (List)JSONParser.parseStream(jsonStream).stream().map((m) -> {
            return (String)((String)((Map)m).get("Ref"));
        }).map(Cid::decode).collect(Collectors.toList());
    }

    public Map resolve(String scheme, Multihash hash, boolean recursive) throws IOException {
        return this.retrieveMap("resolve?arg=/" + scheme + "/" + hash + "&r=" + recursive);
    }

    public String dns(String domain, boolean recursive) throws IOException {
        Map res = this.retrieveMap("dns?arg=" + domain + "&r=" + recursive);
        return (String)res.get("Path");
    }

    public Map mount(java.io.File ipfsRoot, java.io.File ipnsRoot) throws IOException {
        if (ipfsRoot != null && !ipfsRoot.exists()) {
            ipfsRoot.mkdirs();
        }

        if (ipnsRoot != null && !ipnsRoot.exists()) {
            ipnsRoot.mkdirs();
        }

        return (Map)this.retrieveAndParse("mount?arg=" + (ipfsRoot != null ? ipfsRoot.getPath() : "/ipfs") + "&arg=" + (ipnsRoot != null ? ipnsRoot.getPath() : "/ipns"));
    }

    public List<MultiAddress> bootstrap() throws IOException {
        return (List)((List<String>)this.retrieveMap("bootstrap/").get("Peers"))
                .stream()
                .flatMap(x -> {
                    try {
                        return Stream.of(new MultiAddress(x));
                    } catch (Exception var2) {
                        return Stream.empty();
                    }
        }).collect(Collectors.toList());
    }



    public Map ping(Multihash target) throws IOException {
        return this.retrieveMap("ping/" + target.toBase58());
    }

    public Map id(Multihash target) throws IOException {
        return this.retrieveMap("id/" + target.toBase58());
    }

    public Map id() throws IOException {
        return this.retrieveMap("id");
    }

    public String version() throws IOException {
        Map m = (Map)this.retrieveAndParse("version");
        return (String)m.get("Version");
    }

    public Map commands() throws IOException {
        return this.retrieveMap("commands");
    }

    public Map log() throws IOException {
        return this.retrieveMap("log/tail");
    }

    public Object update() throws IOException {
        return this.retrieveAndParse("update");
    }

    private Map retrieveMap(String path) throws IOException {
        return (Map)this.retrieveAndParse(path);
    }

    private Object retrieveAndParse(String path) throws IOException {
        byte[] res = this.retrieve(path);
        return JSONParser.parse(new String(res));
    }

    private Stream<Object> retrieveAndParseStream(String path, ForkJoinPool executor) throws IOException {
        BlockingQueue<CompletableFuture<byte[]>> results = new LinkedBlockingQueue();
        InputStream in = this.retrieveStream(path);
        executor.submit(() -> {
            this.getObjectStream(in, (res) -> {
                results.add(CompletableFuture.completedFuture(res));
            }, (err) -> {
                CompletableFuture<byte[]> fut = new CompletableFuture();
                fut.completeExceptionally(err);
                results.add(fut);
            });
        });
        return Stream.generate(() -> {
            try {
                return JSONParser.parse(new String((byte[])((CompletableFuture)results.take()).get()));
            } catch (Exception var2) {
                throw new RuntimeException(var2);
            }
        });
    }

    private void retrieveAndParseStream(String path, Consumer<Object> results, Consumer<IOException> err) throws IOException {
        this.getObjectStream(this.retrieveStream(path), (d) -> {
            results.accept(JSONParser.parse(new String(d)));
        }, err);
    }

    private byte[] retrieve(String path) throws IOException {
        URL target = new URL(this.protocol, this.host, this.port, this.version + path);
        return get(target);
    }

    private static byte[] get(URL target) throws IOException {
        HttpURLConnection conn = (HttpURLConnection)target.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");

        try {
            InputStream in = conn.getInputStream();
            ByteArrayOutputStream resp = new ByteArrayOutputStream();
            byte[] buf = new byte[4096];

            int r;
            while((r = in.read(buf)) >= 0) {
                resp.write(buf, 0, r);
            }

            return resp.toByteArray();
        } catch (ConnectException var6) {
            throw new RuntimeException("Couldn't connect to IPFS daemon at " + target + "\n Is IPFS running?");
        } catch (IOException var7) {
            String err = new String(readFully(conn.getErrorStream()));
            throw new RuntimeException("IOException contacting IPFS daemon.\nTrailer: " + conn.getHeaderFields().get("Trailer") + " " + err, var7);
        }
    }

    private void getObjectStream(InputStream in, Consumer<byte[]> processor, Consumer<IOException> error) {
        byte LINE_FEED = 10;

        try {
            ByteArrayOutputStream resp = new ByteArrayOutputStream();
            byte[] buf = new byte[4096];

            int r;
            while((r = in.read(buf)) >= 0) {
                resp.write(buf, 0, r);
                if (buf[r - 1] == LINE_FEED) {
                    processor.accept(resp.toByteArray());
                    resp.reset();
                }
            }
        } catch (IOException var8) {
            error.accept(var8);
        }

    }

    private List<Object> getAndParseStream(String path) throws IOException {
        InputStream in = this.retrieveStream(path);
        byte LINE_FEED = 10;
        ByteArrayOutputStream resp = new ByteArrayOutputStream();
        byte[] buf = new byte[4096];
        ArrayList res = new ArrayList();

        int r;
        while((r = in.read(buf)) >= 0) {
            resp.write(buf, 0, r);
            if (buf[r - 1] == LINE_FEED) {
                res.add(JSONParser.parse(new String(resp.toByteArray())));
                resp.reset();
            }
        }

        return res;
    }

    private InputStream retrieveStream(String path) throws IOException {
        URL target = new URL("http", this.host, this.port, this.version + path);
        return getStream(target);
    }

    private static InputStream getStream(URL target) throws IOException {
        HttpURLConnection conn = (HttpURLConnection)target.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/json");
        return conn.getInputStream();
    }

    private Map postMap(String path, byte[] body, Map<String, String> headers) throws IOException {
        URL target = new URL(this.protocol, this.host, this.port, this.version + path);
        return (Map)JSONParser.parse(new String(post(target, body, headers)));
    }

    private static byte[] post(URL target, byte[] body, Map<String, String> headers) throws IOException {
        HttpURLConnection conn = (HttpURLConnection)target.openConnection();
        Iterator var4 = headers.keySet().iterator();

        while(var4.hasNext()) {
            String key = (String)var4.next();
            conn.setRequestProperty(key, (String)headers.get(key));
        }

        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        OutputStream out = conn.getOutputStream();
        out.write(body);
        out.flush();
        out.close();
        InputStream in = conn.getInputStream();
        return readFully(in);
    }

    private static final byte[] readFully(InputStream in) throws IOException {
        ByteArrayOutputStream resp = new ByteArrayOutputStream();
        byte[] buf = new byte[4096];

        int r;
        while((r = in.read(buf)) >= 0) {
            resp.write(buf, 0, r);
        }

        return resp.toByteArray();
    }

    private static boolean detectSSL(MultiAddress multiaddress) {
        return multiaddress.toString().contains("/https");
    }

    public class Update {
        public Update() {
        }

        public Object check() throws IOException {
            return IPFS.this.retrieveAndParse("update/check");
        }

        public Object log() throws IOException {
            return IPFS.this.retrieveAndParse("update/log");
        }
    }

    public class Config {
        public Config() {
        }

        public Map show() throws IOException {
            return (Map)IPFS.this.retrieveAndParse("config/show");
        }

        public void replace(NamedStreamable file) throws IOException {
            Multipart m = new Multipart(IPFS.this.protocol + "://" + IPFS.this.host + ":" + IPFS.this.port + IPFS.this.version + "config/replace?stream-channels=true", "UTF-8");
            m.addFilePart("file", Paths.get(""), file);
            String res = m.finish();
        }

        public Object get(String key) throws IOException {
            Map m = (Map)IPFS.this.retrieveAndParse("config?arg=" + key);
            return m.get("Value");
        }

        public Map set(String key, Object value) throws IOException {
            return IPFS.this.retrieveMap("config?arg=" + key + "&arg=" + value);
        }
    }

    public class Stats {
        public Stats() {
        }

        public Map bw() throws IOException {
            return IPFS.this.retrieveMap("stats/bw");
        }
    }

    public class Diag {
        public Diag() {
        }

        public String cmds() throws IOException {
            return new String(IPFS.this.retrieve("diag/cmds?stream-channels=true"));
        }

        public String sys() throws IOException {
            return new String(IPFS.this.retrieve("diag/sys?stream-channels=true"));
        }
    }

    public class Dag {
        public Dag() {
        }

        public byte[] get(Cid cid) throws IOException {
            return IPFS.this.retrieve("dag/get?stream-channels=true&arg=" + cid);
        }

        public MerkleNode put(byte[] object) throws IOException {
            return this.put("json", object, "cbor");
        }

        public MerkleNode put(String inputFormat, byte[] object) throws IOException {
            return this.put(inputFormat, object, "cbor");
        }

        public MerkleNode put(byte[] object, String outputFormat) throws IOException {
            return this.put("json", object, outputFormat);
        }

        public MerkleNode put(String inputFormat, byte[] object, String outputFormat) throws IOException {
            String prefix = IPFS.this.protocol + "://" + IPFS.this.host + ":" + IPFS.this.port + IPFS.this.version;
            Multipart m = new Multipart(prefix + "dag/put/?stream-channels=true&input-enc=" + inputFormat + "&f=" + outputFormat, "UTF-8");
            m.addFilePart("file", Paths.get(""), new ByteArrayWrapper(object));
            String res = m.finish();
            return MerkleNode.fromJSON(JSONParser.parse(res));
        }
    }

    public class Swarm {
        public Swarm() {
        }

        public List<Peer> peers() throws IOException {
            Map m = IPFS.this.retrieveMap("swarm/peers?stream-channels=true");
            return (List)((List)m.get("Peers")).stream().flatMap((json) -> {
                try {
                    return Stream.of(Peer.fromJSON(json));
                } catch (Exception var2) {
                    return Stream.empty();
                }
            }).collect(Collectors.toList());
        }

        public Map<Multihash, List<MultiAddress>> addrs() throws IOException {
            Map m = retrieveMap("swarm/addrs?stream-channels=true");
            return ((Map<String, Object>)m.get("Addrs")).entrySet()
                    .stream()
                    .collect(Collectors.toMap(
                            e -> Multihash.fromBase58(e.getKey()),
                            e -> ((List<String>)e.getValue())
                                    .stream()
                                    .map(MultiAddress::new)
                                    .collect(Collectors.toList())));
        }

        public Map connect(MultiAddress multiAddr) throws IOException {
            Map m = IPFS.this.retrieveMap("swarm/connect?arg=" + multiAddr);
            return m;
        }

        public Map disconnect(MultiAddress multiAddr) throws IOException {
            Map m = IPFS.this.retrieveMap("swarm/disconnect?arg=" + multiAddr);
            return m;
        }
    }

    public class Bootstrap {
        public Bootstrap() {
        }

        public List<MultiAddress> list() throws IOException {
            return IPFS.this.bootstrap();
        }

        public List<MultiAddress> add(MultiAddress addr) throws IOException {
            return (List)((List<String>)IPFS.this.retrieveMap("bootstrap/add?arg=" + addr).get("Peers")).stream().map((x) -> {
                return new MultiAddress(x);
            }).collect(Collectors.toList());
        }

        public List<MultiAddress> rm(MultiAddress addr) throws IOException {
            return this.rm(addr, false);
        }

        public List<MultiAddress> rm(MultiAddress addr, boolean all) throws IOException {
            return (List)((List<String>)IPFS.this.retrieveMap("bootstrap/rm?" + (all ? "all=true&" : "") + "arg=" + addr).get("Peers")).stream().map((x) -> {
                return new MultiAddress(x);
            }).collect(Collectors.toList());
        }
    }

    public class File {
        public File() {
        }

        public Map ls(Multihash path) throws IOException {
            return IPFS.this.retrieveMap("file/ls?arg=" + path);
        }
    }

    public class DHT {
        public DHT() {
        }

        public List<Map<String, Object>> findprovs(Multihash hash) throws IOException {
            return (List)IPFS.this.getAndParseStream("dht/findprovs?arg=" + hash).stream().map((x) -> {
                return (Map)x;
            }).collect(Collectors.toList());
        }

        public Map query(Multihash peerId) throws IOException {
            return IPFS.this.retrieveMap("dht/query?arg=" + peerId.toString());
        }

        public Map findpeer(Multihash id) throws IOException {
            return IPFS.this.retrieveMap("dht/findpeer?arg=" + id.toString());
        }

        public Map get(Multihash hash) throws IOException {
            return IPFS.this.retrieveMap("dht/get?arg=" + hash);
        }

        public Map put(String key, String value) throws IOException {
            return IPFS.this.retrieveMap("dht/put?arg=" + key + "&arg=" + value);
        }
    }

    public class Name {
        public Name() {
        }

        public Map publish(Multihash hash) throws IOException {
            return this.publish(hash, Optional.empty());
        }

        public Map publish(Multihash hash, Optional<String> id) throws IOException {
            return IPFS.this.retrieveMap("name/publish?arg=/ipfs/" + hash + (String)id.map((name) -> {
                return "&key=" + name;
            }).orElse(""));
        }

        public String resolve(Multihash hash) throws IOException {
            Map res = (Map)IPFS.this.retrieveAndParse("name/resolve?arg=" + hash);
            return (String)res.get("Path");
        }
    }

    public class IPFSObject {
        public IPFSObject() {
        }

        public List<MerkleNode> put(List<byte[]> data) throws IOException {
            Multipart m = new Multipart(IPFS.this.protocol + "://" + IPFS.this.host + ":" + IPFS.this.port + IPFS.this.version + "object/put?stream-channels=true", "UTF-8");
            Iterator var3 = data.iterator();

            while(var3.hasNext()) {
                byte[] f = (byte[])var3.next();
                m.addFilePart("file", Paths.get(""), new ByteArrayWrapper(f));
            }

            String res = m.finish();
            return (List)JSONParser.parseStream(res).stream().map((x) -> {
                return MerkleNode.fromJSON((Map)x);
            }).collect(Collectors.toList());
        }

        public List<MerkleNode> put(String encoding, List<byte[]> data) throws IOException {
            if (!"json".equals(encoding) && !"protobuf".equals(encoding)) {
                throw new IllegalArgumentException("Encoding must be json or protobuf");
            } else {
                Multipart m = new Multipart(IPFS.this.protocol + "://" + IPFS.this.host + ":" + IPFS.this.port + IPFS.this.version + "object/put?stream-channels=true&encoding=" + encoding, "UTF-8");
                Iterator var4 = data.iterator();

                while(var4.hasNext()) {
                    byte[] f = (byte[])var4.next();
                    m.addFilePart("file", Paths.get(""), new ByteArrayWrapper(f));
                }

                String res = m.finish();
                return (List)JSONParser.parseStream(res).stream().map((x) -> {
                    return MerkleNode.fromJSON((Map)x);
                }).collect(Collectors.toList());
            }
        }

        public MerkleNode get(Multihash hash) throws IOException {
            Map json = IPFS.this.retrieveMap("object/get?stream-channels=true&arg=" + hash);
            json.put("Hash", hash.toBase58());
            return MerkleNode.fromJSON(json);
        }

        public MerkleNode links(Multihash hash) throws IOException {
            Map json = IPFS.this.retrieveMap("object/links?stream-channels=true&arg=" + hash);
            return MerkleNode.fromJSON(json);
        }

        public Map<String, Object> stat(Multihash hash) throws IOException {
            return IPFS.this.retrieveMap("object/stat?stream-channels=true&arg=" + hash);
        }

        public byte[] data(Multihash hash) throws IOException {
            return IPFS.this.retrieve("object/data?stream-channels=true&arg=" + hash);
        }

        public MerkleNode _new(Optional<String> template) throws IOException {
            if (template.isPresent() && !IPFS.this.ObjectTemplates.contains(template.get())) {
                throw new IllegalStateException("Unrecognised template: " + (String)template.get());
            } else {
                Map json = IPFS.this.retrieveMap("object/new?stream-channels=true" + (template.isPresent() ? "&arg=" + (String)template.get() : ""));
                return MerkleNode.fromJSON(json);
            }
        }

        public MerkleNode patch(Multihash base, String command, Optional<byte[]> data, Optional<String> name, Optional<Multihash> target) throws IOException {
            if (!IPFS.this.ObjectPatchTypes.contains(command)) {
                throw new IllegalStateException("Illegal Object.patch command type: " + command);
            } else {
                String targetPath = "object/patch/" + command + "?arg=" + base.toBase58();
                if (name.isPresent()) {
                    targetPath = targetPath + "&arg=" + (String)name.get();
                }

                if (target.isPresent()) {
                    targetPath = targetPath + "&arg=" + ((Multihash)target.get()).toBase58();
                }

                byte var8 = -1;
                switch(command.hashCode()) {
                    case -1932811651:
                        if (command.equals("append-data")) {
                            var8 = 3;
                        }
                        break;
                    case -1282239066:
                        if (command.equals("add-link")) {
                            var8 = 0;
                        }
                        break;
                    case 1261662284:
                        if (command.equals("rm-link")) {
                            var8 = 1;
                        }
                        break;
                    case 1368900149:
                        if (command.equals("set-data")) {
                            var8 = 2;
                        }
                }

                switch(var8) {
                    case 0:
                        if (!target.isPresent()) {
                            throw new IllegalStateException("add-link requires name and target!");
                        }
                    case 1:
                        if (!name.isPresent()) {
                            throw new IllegalStateException("link name is required!");
                        }

                        return MerkleNode.fromJSON(IPFS.this.retrieveMap(targetPath));
                    case 2:
                    case 3:
                        if (!data.isPresent()) {
                            throw new IllegalStateException("set-data requires data!");
                        }

                        Multipart m = new Multipart(IPFS.this.protocol + "://" + IPFS.this.host + ":" + IPFS.this.port + IPFS.this.version + "object/patch/" + command + "?arg=" + base.toBase58() + "&stream-channels=true", "UTF-8");
                        m.addFilePart("file", Paths.get(""), new ByteArrayWrapper((byte[])data.get()));
                        String res = m.finish();
                        return MerkleNode.fromJSON(JSONParser.parse(res));
                    default:
                        throw new IllegalStateException("Unimplemented");
                }
            }
        }
    }

    public class Block {
        public Block() {
        }

        public byte[] get(Multihash hash) throws IOException {
            return IPFS.this.retrieve("block/get?stream-channels=true&arg=" + hash);
        }

        public List<MerkleNode> put(List<byte[]> data) throws IOException {
            return this.put(data, Optional.empty());
        }

        public List<MerkleNode> put(List<byte[]> data, Optional<String> format) throws IOException {
            List<MerkleNode> res = new ArrayList();
            Iterator var4 = data.iterator();

            while(var4.hasNext()) {
                byte[] value = (byte[])var4.next();
                res.add(this.put(value, format));
            }

            return res;
        }

        public MerkleNode put(byte[] data, Optional<String> format) throws IOException {
            String fmt = (String)format.map((f) -> {
                return "&format=" + f;
            }).orElse("");
            Multipart m = new Multipart(IPFS.this.protocol + "://" + IPFS.this.host + ":" + IPFS.this.port + IPFS.this.version + "block/put?stream-channels=true" + fmt, "UTF-8");

            try {
                m.addFilePart("file", Paths.get(""), new ByteArrayWrapper(data));
                String res = m.finish();
                return (MerkleNode)JSONParser.parseStream(res).stream().map((x) -> {
                    return MerkleNode.fromJSON((Map)x);
                }).findFirst().get();
            } catch (IOException var6) {
                throw new RuntimeException(var6.getMessage(), var6);
            }
        }

        public Map stat(Multihash hash) throws IOException {
            return IPFS.this.retrieveMap("block/stat?stream-channels=true&arg=" + hash);
        }
    }

    public class Pubsub {
        public Pubsub() {
        }

        public Object ls() throws IOException {
            return IPFS.this.retrieveAndParse("pubsub/ls");
        }

        public Object peers() throws IOException {
            return IPFS.this.retrieveAndParse("pubsub/peers");
        }

        public Object peers(String topic) throws IOException {
            return IPFS.this.retrieveAndParse("pubsub/peers?arg=" + topic);
        }

        public Object pub(String topic, String data) throws Exception {
            return IPFS.this.retrieveAndParse("pubsub/pub?arg=" + topic + "&arg=" + data);
        }

        public Stream<Map<String, Object>> sub(String topic) throws Exception {
            return this.sub(topic, ForkJoinPool.commonPool());
        }

        public Stream<Map<String, Object>> sub(String topic, ForkJoinPool threadSupplier) throws Exception {
            return IPFS.this.retrieveAndParseStream("pubsub/sub?arg=" + topic, threadSupplier).map((obj) -> {
                return (Map)obj;
            });
        }

        public void sub(String topic, Consumer<Map<String, Object>> results, Consumer<IOException> error) throws IOException {
            IPFS.this.retrieveAndParseStream("pubsub/sub?arg=" + topic, (res) -> {
                results.accept((Map)res);
            }, error);
        }
    }

    public class Repo {
        public Repo() {
        }

        public Object gc() throws IOException {
            return IPFS.this.retrieveAndParse("repo/gc");
        }
    }

    public class Key {
        public Key() {
        }

        public KeyInfo gen(String name, Optional<String> type, Optional<String> size) throws IOException {
            return KeyInfo.fromJson(IPFS.this.retrieveAndParse("key/gen?arg=" + name + (String)type.map((t) -> {
                return "&type=" + t;
            }).orElse("") + (String)size.map((s) -> {
                return "&size=" + s;
            }).orElse("")));
        }

        public List<KeyInfo> list() throws IOException {
            return (List)((List)((Map)IPFS.this.retrieveAndParse("key/list")).get("Keys")).stream().map(KeyInfo::fromJson).collect(Collectors.toList());
        }

        public Object rename(String name, String newName) throws IOException {
            return IPFS.this.retrieveAndParse("key/rename?arg=" + name + "&arg=" + newName);
        }

        public List<KeyInfo> rm(String name) throws IOException {
            return (List)((List)((Map)IPFS.this.retrieveAndParse("key/rm?arg=" + name)).get("Keys")).stream().map(KeyInfo::fromJson).collect(Collectors.toList());
        }
    }

    public class Pin {
        public Pin() {
        }

        public List<Multihash> add(Multihash hash) throws IOException {
            return (List)((List)((Map)IPFS.this.retrieveAndParse("pin/add?stream-channels=true&arg=" + hash)).get("Pins")).stream().map((x) -> {
                return Cid.decode((String)x);
            }).collect(Collectors.toList());
        }

        public Map<Multihash, Object> ls() throws IOException {
            return this.ls(PinType.direct);
        }

        public Map<Multihash, Object> ls(PinType type) throws IOException {
            return (Map)((Map<String, Object>)((Map)((Map)IPFS.this.retrieveAndParse("pin/ls?stream-channels=true&t=" + type.name())).get("Keys"))).entrySet().stream().collect(Collectors.toMap((x) -> {
                return Cid.decode((String)x.getKey());
            }, (x) -> {
                return x.getValue();
            }));
        }

        public List<Multihash> rm(Multihash hash) throws IOException {
            return this.rm(hash, true);
        }

        public List<Multihash> rm(Multihash hash, boolean recursive) throws IOException {
            Map json = IPFS.this.retrieveMap("pin/rm?stream-channels=true&r=" + recursive + "&arg=" + hash);
            return (List)((List)json.get("Pins")).stream().map((x) -> {
                return Cid.decode((String)x);
            }).collect(Collectors.toList());
        }

        public List<MultiAddress> update(Multihash existing, Multihash modified, boolean unpin) throws IOException {
            return (List)((List)((Map)IPFS.this.retrieveAndParse("pin/update?stream-channels=true&arg=" + existing + "&arg=" + modified + "&unpin=" + unpin)).get("Pins")).stream().map((x) -> {
                return new MultiAddress((String)x);
            }).collect(Collectors.toList());
        }
    }

    public class Refs {
        public Refs() {
        }

        public List<Multihash> local() throws IOException {
            String jsonStream = new String(IPFS.this.retrieve("refs/local"));
            return (List)JSONParser.parseStream(jsonStream).stream().map((m) -> {
                return (String)((String)((Map)m).get("Ref"));
            }).map(Cid::decode).collect(Collectors.toList());
        }
    }

    public static enum PinType {
        all,
        direct,
        indirect,
        recursive;

        private PinType() {
        }
    }
}
